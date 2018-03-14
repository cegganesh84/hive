/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hive.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

/**
 * This tests Altus integration.
 */
public class TestAltusIntegration {

  /**
   * Test that Altus integration works properly for nonsecure clusters
   */
  @Test
  public void testAltusIntegrationNonsecureADB() throws SQLException {
    Properties connProp = new Properties();
    HiveConnection connection =
        new HiveConnection("jdbc:hive2://ganeshk-non-secure", connProp);
    Assert.assertNotNull(connection);
    Statement stmt = connection.createStatement();
    stmt.executeQuery("show databases;");
    ResultSet res = stmt.getResultSet();
    assertTrue(res.next());
    assertEquals("_impala_builtins", res.getString(1));
    res.close();
    stmt.close();
    connection.close();
  }

  /**
   * Test that Altus integration works properly for secure clusters
   */
  @Test
  public void testAltusIntegrationSecureADB() throws SQLException {
    Properties connProp = new Properties();
    HiveConnection connection =
        new HiveConnection("jdbc:hive2://ganeshk_secure", connProp);
    Assert.assertNotNull(connection);
    Statement stmt = connection.createStatement();
    stmt.executeQuery("show databases;");
    ResultSet res = stmt.getResultSet();
    assertTrue(res.next());
    assertEquals("_impala_builtins", res.getString(1));
    res.close();
    stmt.close();
    connection.close();
  }
}
