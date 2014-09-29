/*
 * Copyright (C) 2014 CloudBindle
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.cloudbindle.youxia.listing;

import com.google.common.collect.Maps;
import io.cloudbindle.youxia.util.ConfigTools;
import java.util.Map;
import java.util.Map.Entry;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.domain.ComputeMetadata;
import org.jclouds.compute.domain.NodeMetadata;

/**
 * This lists instances available on AWS.
 * 
 * @author dyuen
 */
public class AwsJCloudsListing implements InstanceListingInterface {

    @Override
    public Map<String, String> getInstances() {
        String managedTag = ConfigTools.getYouxiaConfig().getString(ConfigTools.YOUXIA_MANAGED_TAG);
        ComputeServiceContext context = ConfigTools.getAmazonComputeContext();
        Map<String, String> map = Maps.newHashMap();
        for (ComputeMetadata node : context.getComputeService().listNodes()) {
            for (Entry<String, String> tag : node.getUserMetadata().entrySet()) {
                if (tag.getKey().equals(ConfigTools.YOUXIA_MANAGED_TAG) && tag.getValue().equals(managedTag)) {
                    if (node instanceof NodeMetadata) {
                        NodeMetadata nodeMetadata = (NodeMetadata) node;
                        if (nodeMetadata.getPublicAddresses().size() <= 0) {
                            System.err.println("Node " + nodeMetadata.getProviderId() + " had no public ip address, skipping");
                            continue;
                        }
                        String ipAddress = nodeMetadata.getPublicAddresses().iterator().next();
                        map.put(nodeMetadata.getProviderId(), ipAddress);
                    }
                }
            }
        }

        System.out.println("Located " + map.values().size() + " relevant instances on AWS");
        return map;
    }

    public static void main(String[] args) {
        AwsJCloudsListing lister = new AwsJCloudsListing();
        Map<String, String> instances = lister.getInstances();
        for (Entry<String, String> instance : instances.entrySet()) {
            System.out.println(instance.getKey() + " " + instance.getValue());
        }
    }

}