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

import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.Tag;
import com.google.common.collect.Maps;
import io.cloudbindle.youxia.util.ConfigTools;
import io.cloudbindle.youxia.util.Constants;
import io.cloudbindle.youxia.util.Log;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.configuration.HierarchicalINIConfiguration;

/**
 * This lists instances available on AWS.
 * 
 * @author dyuen
 */
public class AwsListing implements InstanceListingInterface {

    @Override
    public Map<String, String> getInstances(boolean liveInstances) {
        HierarchicalINIConfiguration youxiaConfig = ConfigTools.getYouxiaConfig();
        String managedTagValue = youxiaConfig.getString(ConfigTools.YOUXIA_MANAGED_TAG);
        AmazonEC2Client ec2 = ConfigTools.getEC2Client();
        Map<String, String> map = Maps.newHashMap();
        // TODO: we can probably constrain instance listing to one region or zone
        DescribeInstancesResult describeInstancesResult = ec2.describeInstances();
        for (Reservation reservation : describeInstancesResult.getReservations()) {
            for (Instance instance : reservation.getInstances()) {
                Tag managedTag = null;
                Tag managedState = null;
                for (Tag tag : instance.getTags()) {
                    if (tag.getKey().equals(ConfigTools.YOUXIA_MANAGED_TAG) && tag.getValue().equals(managedTagValue)) {
                        managedTag = tag;
                    }
                    if (tag.getKey().equals(Constants.STATE_TAG)) {
                        managedState = tag;
                    }
                }
                if (managedTag != null && managedState != null) {
                    if (liveInstances) {
                        if (!(managedState.getValue().equals(Constants.STATE.READY.toString()) || managedState.getValue().equals(
                                Constants.STATE.SETTING_UP.toString()))) {
                            continue;
                        }
                        if (instance.getPublicIpAddress() == null) {
                            Log.info("Node " + instance.getInstanceId() + " had no public ip address, skipping");
                            continue;
                        }
                    } else {
                        if (!managedState.getValue().equals(Constants.STATE.MARKED_FOR_DEATH.toString())) {
                            continue;
                        }
                    }
                    map.put(instance.getInstanceId(), instance.getPublicIpAddress());
                }
            }
        }
        Log.info("Located " + map.values().size() + " relevant instances on AWS");
        return map;
    }

    public static void main(String[] args) {
        AwsListing lister = new AwsListing();
        Map<String, String> instances = lister.getInstances(true);
        for (Entry<String, String> instance : instances.entrySet()) {
            System.out.println(instance.getKey() + " " + instance.getValue());
        }
    }

}
