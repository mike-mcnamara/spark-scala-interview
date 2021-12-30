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
package de.example.spark.testing.app

import de.example.spark.testing.job.AwesomeJob
import de.example.spark.testing.service.AwesomeService
import org.apache.spark.sql.SparkSession


/**
 * AwesomeApp is an application that reads a CSV file from S3.
 * It loads the contents of the CSV in a DataFrame object, which is
 * a row-based data structure used by Apache Spark. It can be thought of
 * as a SQL table.
 *
 * AwesomeApp retrieves the schema of the table. It then changes all of the
 * column names so that they are lowercase. It then writes the DataFrame
 * out to Scylla, which is just some database that can accept a DataFrame as input.
 */
object AwesomeApp extends App {
  private val sourcePath = args(0) // "s3a://some/awesome/source/path/"

  private implicit val sparkSession: SparkSession = SparkSession
    .builder()
    .appName("awesome-app")
    .master("local")
    .config("spark.cassandra.connection.host", "127.0.0.1")
    .getOrCreate()

  private val awesomeService = new AwesomeService

  new AwesomeJob(sourcePath, awesomeService).run()
}
