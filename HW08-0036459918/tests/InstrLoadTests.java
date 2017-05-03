
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.impl.MemoryImpl;
import hr.fer.zemris.java.simplecomp.impl.RegistersImpl;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrLoad;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

import static org.mockito.Mockito.*;
import java.util.List;

@SuppressWarnings("javadoc")
@RunWith(MockitoJUnitRunner.class)
public class InstrLoadTests {

	@SuppressWarnings("unchecked")
	@Mock
	private List<InstructionArgument> arguments = mock(List.class);

	@Mock
	private Memory memorija = mock(MemoryImpl.class);

	@Mock
	private Registers registri = mock(RegistersImpl.class);

	@Mock
	private Computer comp = mock(ComputerImpl.class);;

	@Mock
	private InstructionArgument argument1 = mock(InstructionArgument.class);
	private InstructionArgument argument2 = mock(InstructionArgument.class);
	
	@Test
	public void testLoad1() {
		// load r3, @lokacija
		// @lokacija: DEFINEINT 5
		// @lokacija = 12
		
		
		
		when(comp.getRegisters()).thenReturn(registri);
		when(comp.getMemory()).thenReturn(memorija);
		
		when(arguments.size()).thenReturn(2);
		when(arguments.get(0)).thenReturn(argument1);
		when(arguments.get(1)).thenReturn(argument2);
		when(arguments.get(0).isRegister()).thenReturn(true);
		when(arguments.get(1).isNumber()).thenReturn(true);
		
		when(argument2.getValue()).thenReturn(12);
		when(argument1.getValue()).thenReturn(3);
		when(RegisterUtil.getRegisterIndex((Integer) argument1.getValue())).thenReturn(3);
		

		when(comp.getMemory().getLocation(12)).thenReturn(5);
	
		Instruction load = new InstrLoad(arguments);
		load.execute(comp);
		
		verify(comp, times(1)).getRegisters();
		verify(registri, times(0)).getRegisterValue(0);
		verify(memorija,times(1)).getLocation(12);
     	verify(registri, times(1)).setRegisterValue(3, 5);
     	verify(argument1, times(1)).getValue();
     	verify(argument2, times(1)).getValue();

	}

}
