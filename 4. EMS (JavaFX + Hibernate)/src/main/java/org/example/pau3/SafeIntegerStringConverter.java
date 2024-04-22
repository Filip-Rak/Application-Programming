package org.example.pau3;

import javafx.util.converter.IntegerStringConverter;

public class SafeIntegerStringConverter extends IntegerStringConverter
{

    @Override
    public Integer fromString(String value)
    {
        if (value != null)
        {

            String filteredValue = value.replaceAll("\\D.*", "");
            return super.fromString(filteredValue);
        }
        return null;
    }
}

