package org.firstinspires.ftc.teamcode.subsystems;

import java.util.TreeMap;

/**
 * Linear Interpolated Look-Up Table (LUT)
 * Maps input keys to output values with linear interpolation between points
 */
public class InterpLUT {
    private TreeMap<Double, Double> table;
    private boolean isCreated;

    public InterpLUT() {
        table = new TreeMap<>();
        isCreated = false;
    }

    /**
     * Add a key-value pair to the lookup table
     * @param key The input value
     * @param value The output value corresponding to the key
     */
    public void add(double key, double value) {
        if (isCreated) {
            throw new IllegalStateException("Cannot add values after createLUT() has been called");
        }
        table.put(key, value);
    }

    /**
     * Finalize the lookup table for use
     * Must be called after adding all values and before calling get()
     */
    public void createLUT() {
        if (table.isEmpty()) {
            throw new IllegalStateException("Cannot create LUT with no values");
        }
        isCreated = true;
    }

    /**
     * Get an interpolated value from the lookup table
     * @param key The input value to look up
     * @return The interpolated output value
     */
    public double get(double key) {
        if (!isCreated) {
            throw new IllegalStateException("Must call createLUT() before getting values");
        }

        if (table.isEmpty()) {
            return 0.0;
        }

        // If exact key exists, return it
        if (table.containsKey(key)) {
            return table.get(key);
        }

        // Get the surrounding entries
        Double lowerKey = table.floorKey(key);
        Double upperKey = table.ceilingKey(key);

        // If key is below all entries, return the lowest value
        if (lowerKey == null) {
            return table.get(upperKey);
        }

        // If key is above all entries, return the highest value
        if (upperKey == null) {
            return table.get(lowerKey);
        }

        // Linear interpolation between two points
        double lowerValue = table.get(lowerKey);
        double upperValue = table.get(upperKey);

        // Calculate interpolation factor (0 to 1)
        double t = (key - lowerKey) / (upperKey - lowerKey);

        // Linear interpolation formula: y = y1 + t * (y2 - y1)
        return lowerValue + t * (upperValue - lowerValue);
    }

    /**
     * Clear all values from the table and reset state
     */
    public void clear() {
        table.clear();
        isCreated = false;
    }

    /**
     * Get the number of entries in the table
     * @return The number of key-value pairs
     */
    public int size() {
        return table.size();
    }
}