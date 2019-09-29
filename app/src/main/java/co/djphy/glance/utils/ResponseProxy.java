package co.djphy.glance.utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import co.djphy.glance.adapters.HorizontalGenericAdapter;
import co.djphy.glance.model.DailyProgressDetails;
import co.djphy.glance.model.ServicePackageDetails;
import co.djphy.glance.model.WeeklyProgressDetails;
import co.djphy.glance.model.WeeklyReportDetails;

public class ResponseProxy {

    /*public static JSONObject getProxyJsonForRegister(JSONObject request){
        JSONObject jsonObject = ;
        return ;

    }*/

    private static List<Object> weeklyProgressDetailsList = new ArrayList<>();

    public static List<Object> getWeeklyProgressDetailsList() {
        if (weeklyProgressDetailsList.size() > 0)
            return weeklyProgressDetailsList;
        int day = 20;
        for (int i = 0; i<5; i++) {
            int index = 0;
            for (Object object : packageDetailsList.subList(0, 2)) {
                ServicePackageDetails servicePackageDetails = (ServicePackageDetails) object;
                List<Object> dailyProgressDetailsList = mapOfPackageAndDailyReport
                        .get(servicePackageDetails.packageId);

                /*for (Object object1 : mapOfPackageAndDailyReport.get(servicePackageDetails.packageId)) {
                    DailyProgressDetails dailyProgressDetails = (DailyProgressDetails) object1;*/
                    //super(itemViewType, packageId, packageName, bgImageUrl, subPackagesMap, subPackagesChecklistMap);

                    WeeklyReportDetails weeklyProgressDetails = new WeeklyReportDetails(HorizontalGenericAdapter.WEEKLY_REPORT,
                            servicePackageDetails.packageId, servicePackageDetails.packageName,
                            servicePackageDetails.bgImageUrl, servicePackageDetails.subPackagesMap,
                            dailyProgressDetailsList);
                    weeklyProgressDetails.isOverAllPackageComplete =
                            ((DailyProgressDetails) dailyProgressDetailsList.get(0)).isOverAllPackageComplete;
                    if (index == 0)
                        weeklyProgressDetails.isFirstPackage = true;
                    String[] shortnamearr = servicePackageDetails.packageName.split(" ");
                    StringBuilder builder = new StringBuilder();
                    for (String string: shortnamearr){
                        builder.append(string.charAt(0));
                    }
                    weeklyProgressDetails.packageShortName = builder.toString();
                    weeklyProgressDetails.day = "Sept " + String.valueOf(day);
                    weeklyProgressDetailsList.add(weeklyProgressDetails);
                    index++;
                //}
            }
            day--;
        }
        return weeklyProgressDetailsList;
    }

    private static List<Object> dailyProgressDetailsList = new ArrayList<>();
    public static Map<String, List<Object>> mapOfPackageAndDailyReport = new HashMap<>();

    public static List<Object> getDailyProgressList(){
        if (dailyProgressDetailsList.size() > 0)
            return dailyProgressDetailsList;
        int outIndex = 0;
        for (Object object: packageDetailsList){
            ServicePackageDetails servicePackageDetails = (ServicePackageDetails) object;
            Map<String, String> map = servicePackageDetails.subPackagesMap;
            //List<String> subs = new ArrayList<>(map.values());
            int index = 0;
            List<Object> tempList = new ArrayList<>();
            for (Map.Entry<String, String> entry: map.entrySet()){
                boolean val = false;
                if (outIndex == 0)
                    val = true;
                if (outIndex == 2)
                    val = true;
                TreeMap<String, DailyProgressDetails.ChecklistData> dataTreeMap = new TreeMap<>();
                DailyProgressDetails.ChecklistData checklistData
                        = new DailyProgressDetails.ChecklistData("task1", true, null);
                dataTreeMap.put("FCMMa01", checklistData);
                checklistData
                        = new DailyProgressDetails.ChecklistData("task2", true, null);
                dataTreeMap.put("FCMMa02", checklistData);
                checklistData
                        = new DailyProgressDetails.ChecklistData("task3", true, null);
                dataTreeMap.put("FCMMa03", checklistData);
                checklistData
                        = new DailyProgressDetails.ChecklistData("task4", val,
                        "generic issue raised");
                dataTreeMap.put("FCMMa04", checklistData);
                checklistData
                        = new DailyProgressDetails.ChecklistData("task5", true, null);
                dataTreeMap.put("FCMMa05", checklistData);
                String packageName = null;
                if (index == 0)
                 packageName = servicePackageDetails.packageName;
                DailyProgressDetails dailyProgressDetails
                        = new DailyProgressDetails(servicePackageDetails.itemViewType
                        , servicePackageDetails.packageId, packageName
                        , servicePackageDetails.bgImageUrl, servicePackageDetails.subPackagesMap, dataTreeMap);
                dailyProgressDetails.subPackageId = entry.getKey();
                dailyProgressDetails.subPackageName = entry.getValue();
                dailyProgressDetails.isOverAllPackageComplete = val;
                tempList.add(dailyProgressDetails);
                dailyProgressDetailsList.add(dailyProgressDetails);
                index++;
            }
            mapOfPackageAndDailyReport.put(servicePackageDetails.packageId, tempList);
            outIndex++;
        }
        return dailyProgressDetailsList;
    }

    private static List<Object> packageDetailsList = new ArrayList<>();

    public static List<Object>  getPackageDetailsList(){
        if (packageDetailsList.size() > 0)
            return packageDetailsList;
        TreeMap<String, String> map = new TreeMap<>();
        map.put("FMSsub01", "Masonry");
        map.put("FMSsub02", "Carpentry Works");
        map.put("FMSsub03", "Flooring Works");
        map.put("FMSsub04", "Fabrication Works");
        map.put("FMSsub05", "Painting Works");
        map.put("FMSsub06", "AC Repairs & installation");
        ServicePackageDetails details = new ServicePackageDetails(HorizontalGenericAdapter.AVAILABLE_PACKAGES
                ,"packId"+"01", "Facility management services",
                "dummyurl", map);
        packageDetailsList.add(details);
        TreeMap<String, String> map1 = new TreeMap<>();
        map1.put("PMsub01", "Power Distribution");
        map1.put("PMsub02", "Energy Management");
        map1.put("PMsub03", "Diesel Generators");
        map1.put("PMsub04", "HVAC Systems");
        map1.put("PMsub05", "Transformers etc");
        details = new ServicePackageDetails(HorizontalGenericAdapter.AVAILABLE_PACKAGES
                ,"packId"+"02", "Power Management",
                "dummyurl", map1);
        packageDetailsList.add(details);
        TreeMap<String, String> map2 = new TreeMap<>();
        map2.put("IWsub01", "Woodend Partitions");
        map2.put("IWsub02", "Kitchen Cabinets");
        map2.put("IWsub03", "Bedroom cots & study tables");
        map2.put("IWsub04", "TV cabinets");
        map2.put("IWsub05", "False ceiling works, etc");
        details = new ServicePackageDetails(HorizontalGenericAdapter.AVAILABLE_PACKAGES
                ,"packId"+"03", "Interior Works",
                "dummyurl", map2);
        packageDetailsList.add(details);

        TreeMap<String, String> map3 = new TreeMap<>();
        map3.put("WMsub01", "Plumbing & sewage systems");
        map3.put("WMsub02", "Sewage treatment & water treatment systems");
        map3.put("WMsub03", "Pumps & pumping system");
        map3.put("WMsub04", "Swimming pool maintenance");
        details = new ServicePackageDetails(HorizontalGenericAdapter.AVAILABLE_PACKAGES
                ,"packId"+"04", "Water Management",
                "dummyurl", map3);
        packageDetailsList.add(details);

        TreeMap<String, String> map4 = new TreeMap<>();
        map4.put("PMSsub01", "Property Management");
        map4.put("PMSsub02", "Property Maintenance");
        map4.put("PMSsub03", "Rent Management");
        map4.put("PMSsub04", "Statutory payments");
        details = new ServicePackageDetails(HorizontalGenericAdapter.AVAILABLE_PACKAGES
                ,"packId"+"05", "Property Management services",
                "dummyurl", map4);
        packageDetailsList.add(details);

        TreeMap<String, String> map5 = new TreeMap<>();
        map5.put("GCGPIsub01", "Grass cutting");
        map5.put("GCGPIsub02", "Hedge trimming");
        map5.put("GCGPIsub03", "Weed Control");
        map5.put("GCGPIsub04", "Garden clean up");
        map5.put("GCGPIsub05", "Strimmer work");
        map5.put("GCGPIsub06", "All Gas pipe installation & repairs");
        details = new ServicePackageDetails(HorizontalGenericAdapter.AVAILABLE_PACKAGES
                ,"packId"+"06", "Gardening & Copper Gas pipe installation",
                "dummyurl", map5);
        packageDetailsList.add(details);
        return packageDetailsList;
    }
}
