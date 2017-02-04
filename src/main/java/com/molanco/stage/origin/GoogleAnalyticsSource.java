/**
 * Copyright 2015 StreamSets Inc.
 *
 * Licensed under the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.molanco.stage.origin;

import com.molanco.stage.lib.Errors;
import com.streamsets.pipeline.api.BatchMaker;
import com.streamsets.pipeline.api.Field;
import com.streamsets.pipeline.api.Record;
import com.streamsets.pipeline.api.StageException;
import com.streamsets.pipeline.api.base.BaseSource;
import com.google.api.services.analytics.model.GaData;

//Authentication
import com.molanco.stage.lib.LoadGoogleAnalyticsData;
import com.molanco.stage.lib.OAuth2ClientCredentials;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Reads data from Google Analytics
 * It does however, generate generate a simple record with one field.
 */
public abstract class GoogleAnalyticsSource extends BaseSource {

  /**
   * Gives access to the UI configuration of the stage provided by the {@link GoogleAnalyticsDSource} class.
   */
  public abstract String getConfig();

  public abstract String getService_account();

  public abstract String getAppication_name();

  public abstract String getMetrics();

  public abstract String getDimensions();

  public abstract String getStart_date();

  public abstract String getEnd_date();

  public abstract String getFilter();

  public abstract String getSort();

  /**
   'start-date': lastNDays(14),
   'end-date': lastNDays(0),
   'dimensions': 'ga:date,ga:year,ga:month,ga:week,ga:day',
   'metrics': 'ga:visitors'
   */
  @Override
  protected List<ConfigIssue> init() {
    // Validate configuration values and open any required resources.
    List<ConfigIssue> issues = super.init();

    if (getConfig().equals("invalidValue")) {
      issues.add(
          getContext().createConfigIssue(
              Groups.SAMPLE.name(), "config", Errors.SAMPLE_00, "Here's what's wrong..."
          )
      );
    }

    if (getAppication_name().equals("invalidValue")) {
      issues.add(
              getContext().createConfigIssue(
                      Groups.SAMPLE.name(), "Application_Name", Errors.SAMPLE_00, "Here's what's wrong..."
              )
      );
    }

    if (getDimensions().equals("invalidValue")) {
      issues.add(
              getContext().createConfigIssue(
                      Groups.SAMPLE.name(), "Dimensions", Errors.SAMPLE_00, "Here's what's wrong..."
              )
      );
    }

    if (getEnd_date().equals("invalidValue")) {
      issues.add(
              getContext().createConfigIssue(
                      Groups.SAMPLE.name(), "End_date", Errors.SAMPLE_00, "Here's what's wrong..."
              )
      );
    }

    if (getFilter().equals("invalidValue")) {
      issues.add(
              getContext().createConfigIssue(
                      Groups.SAMPLE.name(), "Filter", Errors.SAMPLE_00, "Here's what's wrong..."
              )
      );
    }

    if (getMetrics().equals("invalidValue")) {
      issues.add(
              getContext().createConfigIssue(
                      Groups.SAMPLE.name(), "Metrics", Errors.SAMPLE_00, "Here's what's wrong..."
              )
      );
    }

    if (getService_account().equals("invalidValue")) {
      issues.add(
              getContext().createConfigIssue(
                      Groups.SAMPLE.name(), "Service_account", Errors.SAMPLE_00, "Here's what's wrong..."
              )
      );
    }

    if (getSort().equals("invalidValue")) {
      issues.add(
              getContext().createConfigIssue(
                      Groups.SAMPLE.name(), "Sort", Errors.SAMPLE_00, "Here's what's wrong..."
              )
      );
    }
    if (getStart_date().equals("invalidValue")) {
      issues.add(
              getContext().createConfigIssue(
                      Groups.SAMPLE.name(), "Start_date", Errors.SAMPLE_00, "Here's what's wrong..."
              )
      );
    }

    // If issues is not empty, the UI will inform the user of each configuration issue in the list.
    return issues;
  }

  /** {@inheritDoc} */
  @Override
  public void destroy() {
    // Clean up any open resources.
    super.destroy();
  }

  /** {@inheritDoc} */
  @Override
  public String produce(String lastSourceOffset, int maxBatchSize, BatchMaker batchMaker) throws StageException {
    // Offsets can vary depending on the data source. Here we use an integer as an example only.
    long nextSourceOffset = 0;
    if (lastSourceOffset != null) {
      nextSourceOffset = Long.parseLong(lastSourceOffset);
    }

    int numRecords = 0;

    try {
      // Create records and add to batch. Records must have a string id. This can include the source offset
      // or other metadata to help uniquely identify the record itself.

      OAuth2ClientCredentials.setAPPLICATION_NAME(getAppication_name());
      OAuth2ClientCredentials.setPATH_P12KEY_SERVICE_ACCOUNT(getConfig());
      OAuth2ClientCredentials.setSERVICE_ACCOUNT_EMAIL(getService_account());
      GaData datas = LoadGoogleAnalyticsData.loadData(getStart_date(), getEnd_date(), getMetrics(), getDimensions());
      List<List<String>> rows =   datas.getRows();

      Iterator<List<String>>   iterato = rows.iterator();




      while (numRecords < maxBatchSize && iterato.hasNext()) {
        List<String> row = iterato.next();

        Record record = getContext().createRecord("some-id::" + nextSourceOffset);
        Map<String, Field> map = new HashMap<>();
        map.put("id", Field.create(nextSourceOffset));
        String[]dataRecord = getDimensions().split(",");
        int mStart =0;
        for(int i=0;i<dataRecord.length;i++){
          map.put(dataRecord[i].split(":")[1], Field.create(row.get(i)));
          mStart = i ;
        }
        mStart++;
        String[]dataRecordMetrics = getMetrics().split(",");
        for(int i=0;i<dataRecordMetrics.length;i++){
          map.put(dataRecordMetrics[i].split(":")[1], Field.create(row.get(i+mStart)));
        }

        record.set(Field.create(map));
        batchMaker.addRecord(record);
        ++nextSourceOffset;
        ++numRecords;
      }

      return String.valueOf(nextSourceOffset);
    } catch (IOException | GeneralSecurityException ex) {
      Logger.getLogger(GoogleAnalyticsSource.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

}
