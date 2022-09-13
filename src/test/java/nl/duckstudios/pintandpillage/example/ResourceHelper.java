package nl.duckstudios.pintandpillage.example;

import nl.duckstudios.pintandpillage.model.ResourceType;
import java.util.HashMap;

public class ResourceHelper {
    public HashMap<String, Integer> generateResource(ResourceType resourceType, int amount){
        HashMap<String, Integer> mockResources = new HashMap() {
            {
                put(resourceType.name(), amount);
            }};

        return mockResources;
    }

    public HashMap<String, Integer> generateResource(ResourceType resourceType, int amount, ResourceType resourceType2, int amount2){
        HashMap<String, Integer> mockResources = new HashMap() {
            {
                put(resourceType.name(), amount);
                put(resourceType2.name(), amount2);
            }};

        return mockResources;
    }

    public HashMap<String, Integer> generateResource(ResourceType resourceType, int amount, ResourceType resourceType2, int amount2, ResourceType resourceType3, int amount3){
        HashMap<String, Integer> mockResources = new HashMap() {
            {
                put(resourceType.name(), amount);
                put(resourceType2.name(), amount2);
                put(resourceType3.name(), amount3);
            }};

        return mockResources;
    }
}
