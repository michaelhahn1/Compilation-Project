package IR;

import MIPS.sir_MIPS_a_lot;
import TEMP.*;

public class IRcommand_Exit_With_Error extends IRcommand{
	
	public IRcommand_Exit_With_Error(TEMP error_temp, int line) {
		this.temp_in1 = error_temp;
		this.line = line;
		
	}
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().print_string(temp_in1, line);
		sir_MIPS_a_lot.getInstance().exit(line);
	}
}
