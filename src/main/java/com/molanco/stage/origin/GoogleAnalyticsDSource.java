/**
 * Copyright 2015 StreamSets Inc.
 * <p>
 * Licensed under the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.molanco.stage.origin;

import com.streamsets.pipeline.api.ConfigDef;
import com.streamsets.pipeline.api.ConfigGroups;
import com.streamsets.pipeline.api.ExecutionMode;
import com.streamsets.pipeline.api.GenerateResourceBundle;
import com.streamsets.pipeline.api.StageDef;

@StageDef(
        version = 1,
        label = "Google Analytics API",
        description = "Origin to retrieve Google Analytics data.",
        icon = "default.png",
        execution = ExecutionMode.STANDALONE,
        recordsByRef = true,
        onlineHelpRefUrl = ""
)
@ConfigGroups(value = Groups.class)
@GenerateResourceBundle
public class GoogleAnalyticsDSource extends GoogleAnalyticsSource {

    @ConfigDef(
            required = false,
            type = ConfigDef.Type.STRING,
            label = "Google Application Name",
            displayPosition = 10,
            group = "SAMPLE"
    )
    public String application_name;

    /**
     * {@inheritDoc}
     */
    @Override

    public String getAppication_name() {
        return application_name;
    }

    @ConfigDef(
            required = true,
            type = ConfigDef.Type.TEXT,
            label = "Service account email",
            description = "Example: 967226286002-compute@developer.gserviceaccount.com",
            displayPosition = 11,
            group = "SAMPLE"
    )
    public String service_account;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getService_account() {
        return service_account;
    }

    @ConfigDef(
            required = true,
            type = ConfigDef.Type.STRING,
            label = ".p12 Path key file location",
            description = "Example: /var/file.p12",
            displayPosition = 12,
            group = "SAMPLE"
    )
    public String config;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getConfig() {
        return config;
    }

    @ConfigDef(
            required = true,
            type = ConfigDef.Type.STRING,
            defaultValue = "ga:date,ga:country,ga:city,ga:browser,ga:keyword",
            label = "Dimensions",
            displayPosition = 13,
            description = "Format: ga:dimension1,ga:date,ga:year,ga:month,ga:week,ga:day,ga:country",
            group = "SAMPLE"
    )
    public String dimensions;

    @Override
    public String getDimensions() {
        return dimensions;
    }

    @ConfigDef(
            required = true,
            type = ConfigDef.Type.STRING,
            defaultValue = "7daysAgo",
            label = "Start Date",
            description = "https://developers.google.com/analytics/devguides/reporting/core/v3/reference#startDate",
            displayPosition = 14,
            group = "SAMPLE"
    )
    public String start_date;

    @Override
    public String getStart_date() {
        return start_date;
    }

    @ConfigDef(
            required = true,
            type = ConfigDef.Type.STRING,
            defaultValue = "today",
            label = "End Date",
            displayPosition = 15,
            group = "SAMPLE"
    )
    public String end_date;

    @Override
    public String getEnd_date() {
        return end_date;
    }

    @ConfigDef(
            required = false,
            type = ConfigDef.Type.STRING,
            defaultValue = "",
            label = "Filter",
            displayPosition = 16,
            description = "https://developers.google.com/analytics/devguides/reporting/core/v3/reference#filter",
            group = "SAMPLE"
    )
    public String filter;

    @Override
    public String getFilter() {
        return filter;
    }

    @ConfigDef(
            required = false,
            type = ConfigDef.Type.STRING,
            defaultValue = "",
            label = "Sort",
            displayPosition = 17,
            description = "https://developers.google.com/analytics/devguides/reporting/core/v3/reference#sort",
            group = "SAMPLE"
    )
    public String sort;

    @Override
    public String getSort() {
        return sort;
    }

    @ConfigDef(
            required = true,
            type = ConfigDef.Type.STRING,
            defaultValue = "ga:visitors",
            label = "Metrics",
            displayPosition = 18,
            description = "The aggregated statistics for user activity to your site, such as clicks or pageviews",
            group = "SAMPLE"
    )
    public String metrics;

    @Override
    public String getMetrics() {
        return metrics;
    }
}
