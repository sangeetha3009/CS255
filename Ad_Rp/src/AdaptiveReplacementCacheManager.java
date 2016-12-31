//package com.custardsource.cache.policy.replacement;

import java.util.LinkedHashSet;
import java.util.Queue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import com.custardsource.cache.policy.QueueAdapter;
import QueueAdapter.java;
import LogUtils.java;
//import com.custardsource.cache.util.LogUtils;

public class AdaptiveReplacementCacheManager<T> extends
		ReplacementCacheManager<T, AdaptiveReplacementConfiguration> {
	private static final Log LOG = LogFactory
			.getLog(AdaptiveReplacementCacheManager.class);
	private Queue<T> b1 = new QueueAdapter<T>(new LinkedHashSet<T>());
	private Queue<T> b2 = new QueueAdapter<T>(new LinkedHashSet<T>());
	private int targetT1Size = 0;

	public AdaptiveReplacementCacheManager(
			AdaptiveReplacementConfiguration config) {
		setConfig(config);
		registerQueue(b1, "b1");
		registerQueue(b2, "b2");
	}

	@Override
	public void clear() {
		super.clear();
		b1.clear();
		b2.clear();
	}

	private int directorySize() {
		return t1.size() + b1.size() + t2.size() + b2.size();
	}

	@Override
	public void assertInvariants() {
		assertInvariant(0 <= leftSideSize());
		assertInvariant(leftSideSize() <= cacheCapacity());
		assertInvariant(0 <= directorySize());
		assertInvariant(directorySize() <= directoryCapacity());
	}

	private int directoryCapacity() {
		return getConfig().getMaxNodes() * 2;
	}

	private int leftSideSize() {
		return t1.size() + b1.size();
	}

	@Override
	protected void dumpStatus() {
		if (LOG.isTraceEnabled()) {
            StringBuilder builder = new StringBuilder();
            builder.append("  Target t1 size: " + targetT1Size + "\n");
            builder.append("  " + dumpQueue(t1) + "\n");
            builder.append("  " + dumpQueue(b1) + "\n");
            builder.append("  " + dumpQueue(t2) + "\n");
            builder.append("  " + dumpQueue(b2));
            LOG.trace(builder.toString());
		} else if (LOG.isDebugEnabled()) {
			LOG.debug("  Target t1 size: " + targetT1Size
					+ ", actual capacities " + dumpCapacity(t1) + " "
					+ dumpCapacity(t2) + " " + dumpCapacity(b1) + " "
					+ dumpCapacity(b2));
		}
	}

	@Override
	protected int cacheCapacity() {
		return getConfig().getMaxNodes();
	}

	@Override
	protected void onMiss(T entry) {
		if (leftSideSize() >= cacheCapacity()) {
			LogUtils.debug(LOG, " Side 1 full (IV.i)");
			if (t1.size() < cacheCapacity()) {
				LogUtils.debug(LOG, " b1 contains item, evicting (IV.i.if)");
				removeNode(b1);
				freeRoom(null);
			} else {
				LogUtils.debug(LOG, " b1's empty, evict from t1 (IV.i.else)");
				evictNode(t1);
			}
		} else {
			if (directorySize() >= cacheCapacity()) {
				LogUtils.debug(LOG, " cache full (IV.ii)");
				if (directorySize() >= directoryCapacity()) {
					LogUtils.debug(LOG, " directory full, evict from b2 (IV.ii.if)");
					removeNode(b2);
				}
				freeRoom(null);
			} else {
				LogUtils.debug(LOG, " room in the cache, straight to t1 (IV.fallthrough)");
			}
		}
		insertNode(entry, t1);
	}

	private void freeRoom(Queue<T> hitList) {
		if (!t1.isEmpty()
				&& (t1.size() > targetT1Size || (t1.size() == targetT1Size && hitList == b2))) {
			LogUtils.debug(LOG, " doReplace -- t1 -> b1");
			evict(moveHead(t1, b1));
		} else {
			if (t2.isEmpty()) {
				LogUtils.debug(LOG, " doReplace -- t2 -> b2, but t2 is empty");
			} else {
				LogUtils.debug(LOG, " doReplace -- t2 -> b2");
				evict(moveHead(t2, b2));
			}
		}
	}

	@Override
	protected void onHit(T node, Queue<T> currentLocation) {
		if (currentLocation == t1 || currentLocation == t2) {
			LogUtils.debug(LOG, " Moving from %s to head of t2 (I)",
					queueName(currentLocation));
			moveNode(node, currentLocation, t2);
		} else {
			// It's b1 or b2
			LogUtils.debug(LOG, " Moving from %s to head of t2 (II/III)",
					queueName(currentLocation));
			if (currentLocation == b1) {
				// B1 hit - favour recency
				LogUtils.debug(LOG, "  B1 hit - favour recency (II)");
				targetT1Size = Math.min(targetT1Size
						+ Math.max(b2.size() / b1.size(), 1), cacheCapacity());
			} else {
				// B2 hit - favour frequency
				LogUtils.debug(LOG, "  B2 hit - favour frequency (III)");
				targetT1Size = Math.max(targetT1Size
						- Math.max(b1.size() / b2.size(), 1), 0);
			}
			freeRoom(currentLocation);
			moveNode(node, currentLocation, t2);
		}
	}

    @Override
    protected String debugString() {
        return super.debugString() + " t" + targetT1Size;
    }

}