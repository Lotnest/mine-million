package dev.lotnest.minemillion.util;

import dev.lotnest.minemillion.MineMillionPlugin;
import org.apache.commons.lang3.StringUtils;

public class VersionUtil {

    private VersionUtil() {
    }

    public static String getVersion() {
        return MineMillionPlugin.getInstance().getDescription().getVersion();
    }

    public static boolean isTheSameVersion(String versionToCheckAgainstCurrent) {
        return StringUtils.equals(getVersion(), versionToCheckAgainstCurrent);
    }

    public static boolean isNewerVersion(String versionToCheckAgainstCurrent) {
        if (StringUtils.isBlank(versionToCheckAgainstCurrent)) {
            return false;
        }

        String[] currentParts = getVersion().replaceAll("-SNAPSHOT$", "").split("\\.");
        String[] latestParts = versionToCheckAgainstCurrent.replaceAll("-SNAPSHOT$", "").split("\\.");
        int maxLength = Math.max(currentParts.length, latestParts.length);

        for (int i = 0; i < maxLength; i++) {
            int currentPart = (i < currentParts.length) ? Integer.parseInt(currentParts[i]) : 0;
            int latestPart = (i < latestParts.length) ? Integer.parseInt(latestParts[i]) : 0;

            if (currentPart < latestPart) {
                return true;
            } else if (currentPart > latestPart) {
                return false;
            }
        }

        return false;
    }
}
