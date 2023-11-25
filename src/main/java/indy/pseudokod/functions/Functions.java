package indy.pseudokod.functions;

import indy.pseudokod.environment.Environment;
import indy.pseudokod.exceptions.ArgumentsAmountException;
import indy.pseudokod.exceptions.InvalidConversionDataTypeException;
import indy.pseudokod.runtime.values.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Functions {

    public static RuntimeValue date(List<RuntimeValue> args, Environment env) throws Throwable {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        return new StringValue(format.format(date));
    }

    public static RuntimeValue time(List<RuntimeValue> args, Environment env) throws Throwable {
        Date time = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        return new StringValue(format.format(time));
    }
}
