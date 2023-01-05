package ru.liner.facerapp.decoder;


/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 22.12.2022, четверг
 **/
public class WeatherDecoder extends Decoder {

    public WeatherDecoder(String value) {
        super(value);
    }

    @Override
    public boolean onElement(String element, StringBuilder stringBuilder) {
        if(element.contains("#"))
            element = element.replace("#", "");
        switch (element) {
            case "WM":
                stringBuilder.append("F"); //Weather Units (F/C)
                return true;
            case "WLC":
                stringBuilder.append("Los Angeles"); //Weather Location
                return true;
            case "WTH":
                stringBuilder.append("86"); //Today's High
                return true;
            case "WTL":
                stringBuilder.append("63"); //Today's Low
                return true;
            case "WCT":
                stringBuilder.append("84"); //Current Temp
                return true;
            case "WCCI":
                stringBuilder.append("3"); //Current Condition Icon
                return true;
            case "WCCT":
                stringBuilder.append("Fair"); //Current Condition Text
                return true;
            case "WCHN":
                stringBuilder.append("40"); //	Current Humidity Number
                return true;
            case "WCHP":
                stringBuilder.append("40").append("%"); //	Current Humidity Percentage
                return true;
            case "DISDAYTIME":
                stringBuilder.append("TRUE"); //Returns true if time is after sunrise and before sunset
                return true;
            case "WRh":
                stringBuilder.append("5"); //Sunrise hour (1-12)
                return true;
            case "WRhZ":
                stringBuilder.append("05"); //Sunrise hour (leading zero) (01-12)
                return true;
            case "WRH":
                stringBuilder.append("5"); //Sunrise hour (0-23)
                return true;
            case "WRHZ":
                stringBuilder.append("5"); //Sunrise hour (leading zero) (00-23)
                return true;
            case "WRm":
                stringBuilder.append("50"); //Sunrise minute (0-59)
                return true;
            case "WRmZ":
                stringBuilder.append("50"); //Sunrise minute (leading zero) (00-59)
                return true;
            case "WSh":
                stringBuilder.append("8"); //Sunset hour (1-12)
                return true;
            case "WShZ":
                stringBuilder.append("8"); //Sunset hour (1-12)
                return true;
            case "WSH":
                stringBuilder.append("8"); //Sunset hour (1-12)
                return true;
            case "WSHZ":
                stringBuilder.append("8"); //Sunset hour (1-12)
                return true;
            case "WSm":
                stringBuilder.append("8"); //Sunset hour (1-12)
                return true;
            case "WSmZ":
                stringBuilder.append("8"); //Sunset hour (1-12)
                return true;
            case "WSUNRISE":
                stringBuilder.append("8"); //Sunset hour (1-12)
                return true;
            case "WSUNSET":
                stringBuilder.append("8"); //Sunset hour (1-12)
                return true;
            case "WSUNRISE24":
                stringBuilder.append("8"); //Sunset hour (1-12)
                return true;
            case "WSUNSET24":
                stringBuilder.append("8"); //Sunset hour (1-12)
                return true;
            case "WSUNRISEH":
                stringBuilder.append("8"); //Sunset hour (1-12)
                return true;
            case "WSUNRISEM":
                stringBuilder.append("8"); //Sunset hour (1-12)
                return true;
            case "WSUNSETH":
                stringBuilder.append("8"); //Sunset hour (1-12)
                return true;
            case "WSUNSETM":
                stringBuilder.append("8"); //Sunset hour (1-12)
                return true;
            case "WSUNRISEH24":
                stringBuilder.append("8"); //Sunset hour (1-12)
                return true;
            case "WSUNSETH24":
                stringBuilder.append("8"); //Sunset hour (1-12)
                return true;
            case "WFAH":
                stringBuilder.append("8"); //Sunset hour (1-12)
                return true;
            case "WFAL":
                stringBuilder.append("8"); //Sunset hour (1-12)
                return true;
            case "WFACT":
                stringBuilder.append("8"); //Sunset hour (1-12)
                return true;
            case "WFACI":
                stringBuilder.append("1"); //Sunset hour (1-12)
                return true;
            case "WFBH":
                stringBuilder.append("1"); //Sunset hour (1-12)
                return true;
            case "WFBL":
                stringBuilder.append("1"); //Sunset hour (1-12)
                return true;
            case "WFBCT":
                stringBuilder.append("1"); //Sunset hour (1-12)
                return true;
            case "WFBCI":
                stringBuilder.append("1"); //Sunset hour (1-12)
                return true;
            case "WFCH":
                stringBuilder.append("1"); //Sunset hour (1-12)
                return true;
            case "WFCL":
                stringBuilder.append("1"); //Sunset hour (1-12)
                return true;
            case "WFCCT":
                stringBuilder.append("1"); //Sunset hour (1-12)
                return true;
            case "WFCCI":
                stringBuilder.append("1"); //Sunset hour (1-12)
                return true;
            case "WFDH":
                stringBuilder.append("1"); //Sunset hour (1-12)
                return true;
            case "WFDL":
                stringBuilder.append("1"); //Sunset hour (1-12)
                return true;
            case "WFDCT":
                stringBuilder.append("Sunny"); //Sunset hour (1-12)
                return true;
            case "WFDCI":
                stringBuilder.append("1"); //Sunset hour (1-12)
                return true;
            case "WFEH":
                stringBuilder.append("86"); //Sunset hour (1-12)
                return true;
            case "WFEL":
                stringBuilder.append("68"); //Sunset hour (1-12)
                return true;
            case "WFECT":
                stringBuilder.append("Mostly Sunny"); //Sunset hour (1-12)
                return true;
            case "WFECI":
                stringBuilder.append("1"); //Sunset hour (1-12)
                return true;
            case "WND":
                stringBuilder.append("5.5"); //Sunset hour (1-12)
                return true;
            case "WNDD":
                stringBuilder.append("45.98889"); //Sunset hour (1-12)
                return true;
            case "WNDDS":
                stringBuilder.append("NNE"); //Sunset hour (1-12)
                return true;
            case "WNDDSS":
                stringBuilder.append("North East"); //Sunset hour (1-12)
                return true;
            default:
                return false;
        }
    }
}
