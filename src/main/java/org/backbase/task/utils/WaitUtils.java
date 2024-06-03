package org.backbase.task.utils;

import lombok.experimental.UtilityClass;
import org.awaitility.Awaitility;
import org.backbase.task.config.Config;

import java.time.Duration;
import java.util.concurrent.Callable;

@UtilityClass
public class WaitUtils {

    public void waitForConditionToMaintainOver(Callable<Boolean> condition, int periodSeconds) {
        var config = Config.getInstance();
        Awaitility.await()
                .atMost(Duration.ofMillis(config.timeout()))
                .pollInterval(Duration.ofMillis(config.pollingInterval()))
                .pollInSameThread()
                .ignoreExceptions()
                .during(Duration.ofSeconds(periodSeconds))
                .until(condition);
    }
}
