/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.shardingsphere.core.constant;

/**
 * Sharding constant.
 * 
 * @author zhangliang
 */
public interface ShardingConstant {
    
    /**
     * Logic database schema name.
     * 
     * <p>
     * Sharding-Sphere just have one logic database table.
     * Default: sharding_db
     * </p>
     */
    String LOGIC_SCHEMA_NAME = "sharding_db";
}
