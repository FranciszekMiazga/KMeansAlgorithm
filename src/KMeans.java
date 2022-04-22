import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

enum Classes{
    CLASS_A,CLASS_B
}
public class KMeans {
    private List<List<Double>> listCentroids;
    private List<Double> listWektors;
    private List<List<Double>> listData;
    private List<List<Double>> listCentroidsGruped;
    private List<Double> listWektors2;
    private Map<Double, List<List<Double>>> hashmap=new LinkedHashMap<Double, List<List<Double>>>();
    String dataFile="Data/bankloans 1.csv";
    boolean loopRunning=true;
    Map <Integer,List<Double>> map=new HashMap<>();

    public void kMeans(int k) throws IOException {
        readFile();
        generateCentroids(k);


        while(loopRunning) {
            calculateDistance();
            extractValues();
            printList(listCentroids);
            System.out.println("-----------------------------");
        }
    }
    private void generateCentroids(int k){
        listCentroids=new ArrayList<>();
        listWektors=new ArrayList<>();
        int max=7,min=0;
        for(int i=0;i<k;i++) {
            for (int j = 0; j < listData.get(i).size(); j++) {
                int rand = (int) (Math.random() * ((max - min) + 1)) + min;
                listWektors.add((double) rand);
            }
            listCentroids.add(listWektors);
            listWektors=new ArrayList<>();
        }
    }

    private void extractValues(){
        List<Double> list=new ArrayList<>();
        int k=0;
        for(Double name: hashmap.keySet()) {
            List<List<Double>> list2 = hashmap.get(name);

            for (int j = 0; j < list2.size(); j++) {
                for (int z = 0; z < list2.get(j).size(); z++) {

                    if(j==0 && j<list2.get(j).size()) {
                        list=new ArrayList<>();
                        double ele=list2.get(0).get(z);
                        list.add(ele);
                        map.put(z, list);
                    }else
                        map.get(z).add(list2.get(j).get(z));
                }
            }
            calculateAverage(name);
        }
    }
    private void calculateDistance(){

        listCentroidsGruped=new ArrayList<>();
        double euclidesDist;
        double euclidesDistMin=0;
        for(int i=0;i<listData.size();i++){
            euclidesDist=0;
            int classes;
            double group=calcDist(listData.get(i));

            if(!hashmap.containsKey(group)) {
                listCentroidsGruped=new ArrayList<>();
                listCentroidsGruped.add(listData.get(i));
                hashmap.put(group, listCentroidsGruped);
            }else
                hashmap.get(group).add(listData.get(i));

        }
    }

    private void calculateAverage(double k){
        int j=0;
        boolean isEquals=true;
        for(Integer name: map.keySet()){
            List<Double> list2 = map.get(name);
            int sum=0;

            for(int i=0;i<list2.size();i++){
                sum+=list2.get(i);
            }
            Double avg= Double.valueOf(sum/list2.size());

            var res=listCentroids.get((int) k);
            int element=res.get(j).intValue();

            if(element!=avg.intValue())
                isEquals=false;

            res.set(j,avg);
            j++;
        }
        if(isEquals)
           loopRunning=false;


    }
    private void printMap(){
        for (Double name: hashmap.keySet()) {
            Double key = name;
            String value = hashmap.get(name).toString();

            System.out.println(key + " " + value);
        }
    }
    private void printMap2(){
        for (Integer name: map.keySet()) {
            Integer key = name;
            String value = map.get(name).toString();

            System.out.println(key + " " + value);
            System.out.println(map.get(name).size());
        }
    }
    private double calcDist(List<Double> list){
        double minDistance=Double.MAX_VALUE;
        double group=-1;
        for(int i=0;i<listCentroids.size();i++){
            double distance=0;

            for(int j=0;j<listCentroids.get(i).size()-1;j++){
                distance+=Math.sqrt(Math.pow((list.get(j)-listCentroids.get(i).get(j)),2));
            }
            if(distance<minDistance) {
                minDistance = distance;
                group=i;
            }

            //System.out.println(distance+" "+minDistance+" "+group);
        }
        return group;
    }
    private void readFile() throws IOException {
        FileReader dataReader=new FileReader(dataFile);
        String line="";
        BufferedReader bufReader=new BufferedReader(dataReader);
        listData=new ArrayList<>();
        listWektors2=new ArrayList<>();

        while((line=bufReader.readLine())!=null){
            String[] arr=line.split(",");
            for (int j = 0; j < arr.length; j++) {
                double data=Double.parseDouble(arr[j]);
                listWektors2.add(data);
            }
            listData.add(listWektors2);
            listWektors2=new ArrayList<>();
        }

    }
    private void printList(List<List<Double>> list){
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
    }
}
