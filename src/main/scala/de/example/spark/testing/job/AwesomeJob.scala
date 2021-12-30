// Author: Gustavo Martin Morcuende

/**
 * Copyright 2020 Gustavo Martin Morcuende
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.example.spark.testing.job

import com.typesafe.scalalogging.LazyLogging
import de.example.spark.testing.job.AwesomeJob.{Database, Table}
import de.example.spark.testing.service.AwesomeService
import org.apache.spark.sql.cassandra._
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

private object AwesomeJob {
  private val Database = "testing"
  private val Table = "example"
}

class AwesomeJob(sourcePath: String, awesomeService: AwesomeService)(implicit sparkSession: SparkSession)
    extends LazyLogging {

  def run(): Unit = {
    logger.info("Running AwesomeJob")

    /** Read data from S3 into a DataFrame object */
    val dataFrame: DataFrame = sparkSession.read.format("csv")
      .option("header", value = true)
      .option("multiline", value = true)
      .option("inferSchema", value = true)
      .load(sourcePath)
    val schema: StructType = dataFrame.schema

    /** Modify the DataFrame's schema */
    val lowerCaseSchema: StructType = awesomeService.renameColumnsToLowerCase(schema)
    val lowerCaseDataFrame: DataFrame = sparkSession.createDataFrame(dataFrame.rdd, lowerCaseSchema)

    /** Write the DataFrame out to storage. */
    lowerCaseDataFrame.write
      .cassandraFormat(table = Table, keyspace = Database, cluster = "scylla")
      .option("confirm.truncate", "true")
      .mode(SaveMode.Overwrite).save()

    logger.info("End running AwesomeJob")
  }
}
