package GLOBAL_VARIABLES;

import java.util.ArrayList;
import java.util.List;

public class FUNCTIONS_DECLARED {
    public List<String> functions_list = new ArrayList<>();
    private static FUNCTIONS_DECLARED instance = null;
    public static FUNCTIONS_DECLARED getInstance()
    {
        if (instance == null)
        {
            /*******************************/
            /* [0] The instance itself ... */
            /*******************************/
            instance = new FUNCTIONS_DECLARED();
        }
        return instance;
    }
}
