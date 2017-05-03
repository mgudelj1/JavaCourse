
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.impl.MemoryImpl;
import hr.fer.zemris.java.simplecomp.impl.RegistersImpl;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrRet;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

import static org.mockito.Mockito.*;
import java.util.List;

@SuppressWarnings("javadoc")
@RunWith(MockitoJUnitRunner.class)
public class InstrRetTests {

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

	@Test
	public void testMove1() {
		// 0  call @lokacija
		// 1
		//55  @lokacija: 

		when(comp.getRegisters()).thenReturn(registri);
		when(comp.getMemory()).thenReturn(memorija);

		when(arguments.size()).thenReturn(0);



		when(registri.getRegisterValue(15)).thenReturn(22);
		when(memorija.getLocation(23)).thenReturn(1);
		

		Instruction ret = new InstrRet(arguments);
		ret.execute(comp);

		verify(registri, times(1)).setRegisterValue(15, 23);
		verify(registri, times(1)).setProgramCounter(1);

	}
}
