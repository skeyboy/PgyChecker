package com.xsk.pgyercheckhelper;

public class PgyerShorCutBean extends PgyerBean {
    Data data;

    @Override
    public String toString() {
        return "PgyerShorCutBean{" +
                "data=" + data +
                '}';
    }

    public  class  Data{
        public String buildKey;
        public String buildType;
        public String buildIsFirst;
        public String buildIsLastest;
        public String buildFileName;
        public String buildFileSize;
        public String buildName;
        public String buildVersion;
        public String buildVersionNo;
        public String buildBuildVersion;
        public String buildIdentifier;
        public String buildUpdateDescription;
        public String buildShortcutUrl;
        public String buildScreenshots;
        public String buildCreated;
        public String buildUpdated;

        @Override
        public String toString() {
            return  "PgyerShorCutBean{" +
                    "buildKey='" + buildKey + '\'' +
                    ", buildType='" + buildType + '\'' +
                    ", buildIsFirst='" + buildIsFirst + '\'' +
                    ", buildIsLastest='" + buildIsLastest + '\'' +
                    ", buildFileName='" + buildFileName + '\'' +
                    ", buildFileSize='" + buildFileSize + '\'' +
                    ", buildName='" + buildName + '\'' +
                    ", buildVersion='" + buildVersion + '\'' +
                    ", buildVersionNo='" + buildVersionNo + '\'' +
                    ", buildBuildVersion='" + buildBuildVersion + '\'' +
                    ", buildIdentifier='" + buildIdentifier + '\'' +
                    ", buildUpdateDescription='" + buildUpdateDescription + '\'' +
                    ", buildShortcutUrl='" + buildShortcutUrl + '\'' +
                    ", buildScreenshots='" + buildScreenshots + '\'' +
                    ", buildCreated='" + buildCreated + '\'' +
                    ", buildUpdated='" + buildUpdated + '\'' +
                    '}';
        }
    }
}
