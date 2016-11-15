/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gridgain;

import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.springframework.core.annotation.Order;

/**
 *
 */
public class ua_parsed_attrs {
    @Order(0)
    @QuerySqlField(index = true)
    private String useragent;

    @Order(1)
    @QuerySqlField(index = true)
    private String browsername;

    @Order(2)
    @QuerySqlField
    private String browserversion;

    @Order(3)
    @QuerySqlField
    private Integer id;

    @Order(4)
    @QuerySqlField
    private String vendor;

    @Order(5)
    @QuerySqlField
    private String model;

    @Order(6)
    @QuerySqlField(index = true)
    private String osname;

    @Order(7)
    @QuerySqlField
    private String fullname;

    @Order(8)
    @QuerySqlField
    private String marketingname;

    @Order(9)
    @QuerySqlField
    private String manufacturer;

    @Order(10)
    @QuerySqlField
    private Integer yearreleased;

    @Order(11)
    @QuerySqlField
    private String primaryhardwaretype;

    @Order(12)
    @QuerySqlField
    private Integer displaywidth;

    @Order(13)
    @QuerySqlField
    private Integer displayheight;

    @Order(14)
    @QuerySqlField
    private String diagonalscreensize;

    @Order(15)
    @QuerySqlField
    private Integer displayppi;

    @Order(16)
    @QuerySqlField
    private String devicepixelratio;

    @Order(17)
    @QuerySqlField
    private Integer displaycolordepth;

    @Order(18)
    @QuerySqlField
    private String isrobot;

    @Order(19)
    @QuerySqlField
    private String botname;

    @Order(20)
    @QuerySqlField
    private String processed_date;
}
