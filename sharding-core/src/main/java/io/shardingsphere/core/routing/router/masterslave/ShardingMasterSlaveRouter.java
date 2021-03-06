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

package io.shardingsphere.core.routing.router.masterslave;

import io.shardingsphere.core.constant.SQLType;
import io.shardingsphere.core.hint.HintManagerHolder;
import io.shardingsphere.core.routing.SQLExecutionUnit;
import io.shardingsphere.core.routing.SQLRouteResult;
import io.shardingsphere.core.rule.MasterSlaveRule;
import io.shardingsphere.core.constant.SQLType;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Sharding with master-slave router interface.
 * 
 * @author zhangiang
 */
@RequiredArgsConstructor
public final class ShardingMasterSlaveRouter {
    
    private final Collection<MasterSlaveRule> masterSlaveRules;
    
    /**
     * Route Master slave after sharding.
     * 
     * @param sqlRouteResult SQL route result
     * @return route result
     */
    public SQLRouteResult route(final SQLRouteResult sqlRouteResult) {
        for (MasterSlaveRule each : masterSlaveRules) {
            route(each, sqlRouteResult);
        }
        return sqlRouteResult;
    }
    
    private void route(final MasterSlaveRule masterSlaveRule, final SQLRouteResult sqlRouteResult) {
        Collection<SQLExecutionUnit> toBeRemoved = new LinkedList<>();
        Collection<SQLExecutionUnit> toBeAdded = new LinkedList<>();
        for (SQLExecutionUnit each : sqlRouteResult.getExecutionUnits()) {
            if (!masterSlaveRule.getName().equalsIgnoreCase(each.getDataSource())) {
                continue;
            }
            toBeRemoved.add(each);
            if (isMasterRoute(sqlRouteResult.getSqlStatement().getType())) {
                MasterVisitedManager.setMasterVisited();
                toBeAdded.add(new SQLExecutionUnit(masterSlaveRule.getMasterDataSourceName(), each.getSqlUnit()));
            } else {
                toBeAdded.add(new SQLExecutionUnit(masterSlaveRule.getLoadBalanceAlgorithm().getDataSource(
                        masterSlaveRule.getName(), masterSlaveRule.getMasterDataSourceName(), new ArrayList<>(masterSlaveRule.getSlaveDataSourceNames())), each.getSqlUnit()));
            }
        }
        sqlRouteResult.getExecutionUnits().removeAll(toBeRemoved);
        sqlRouteResult.getExecutionUnits().addAll(toBeAdded);
    }
    
    private boolean isMasterRoute(final SQLType sqlType) {
        return SQLType.DQL != sqlType || MasterVisitedManager.isMasterVisited() || HintManagerHolder.isMasterRouteOnly();
    }
}
