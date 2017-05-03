
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.impl.MemoryImpl;
import hr.fer.zemris.java.simplecomp.impl.RegistersImpl;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrMove;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

import static org.mockito.Mockito.*;
import java.util.List;

@SuppressWarnings("javadoc")
@RunWith(MockitoJUnitRunner.class)
public class InstrMoveTests {

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
	public void testMove1() {
		// move r2, r1
		// r1 = 5

		when(comp.getRegisters()).thenReturn(registri);
		when(comp.getMemory()).thenReturn(memorija);

		when(arguments.size()).thenReturn(2);

		when(arguments.get(0)).thenReturn(argument1);
		when(argument1.isRegister()).thenReturn(true);
		when(argument1.getValue()).thenReturn(2);

		when(RegisterUtil.getRegisterIndex((Integer) argument1.getValue())).thenReturn(2);

		when(arguments.get(1)).thenReturn(argument2);
		when(argument2.isRegister()).thenReturn(true);
		when(argument2.getValue()).thenReturn(1);

		when(RegisterUtil.getRegisterIndex((Integer) argument2.getValue())).thenReturn(1);

		when(registri.getRegisterValue(1)).thenReturn(5);

		Instruction move = new InstrMove(arguments);
		move.execute(comp);

		verify(registri, times(1)).setRegisterValue(2, 5);
		verify(argument1, times(1)).getValue();
		verify(argument2, times(1)).getValue();

	}

	@Test
	public void testMove2() {
		// move r7, 13
		// r7 = 13

		when(comp.getRegisters()).thenReturn(registri);
		when(comp.getMemory()).thenReturn(memorija);

		when(arguments.size()).thenReturn(2);

		when(arguments.get(0)).thenReturn(argument1);
		when(argument1.isRegister()).thenReturn(true);
		when(argument1.getValue()).thenReturn(2);

		when(RegisterUtil.getRegisterIndex((Integer) argument1.getValue())).thenReturn(7);

		when(arguments.get(1)).thenReturn(argument2);
		when(argument2.isRegister()).thenReturn(false);
		when(argument2.isNumber()).thenReturn(true);
		when(argument2.getValue()).thenReturn(13);

		Instruction move = new InstrMove(arguments);
		move.execute(comp);

		verify(registri, times(1)).setRegisterValue(7, 13);
		verify(argument1, times(1)).getValue();
		verify(argument2, times(1)).getValue();

	}

	@Test
	public void testMove3() {
		// move [r7+2], [r3+1]
		// [r3+1] = 5
		// r7 = 10
		// r3 = 10

		when(comp.getRegisters()).thenReturn(registri);
		when(comp.getMemory()).thenReturn(memorija);

		when(arguments.size()).thenReturn(2);

		when(arguments.get(0)).thenReturn(argument1);
		when(argument1.isRegister()).thenReturn(true);
		when(argument1.getValue()).thenReturn(0x1000207);

	//	when(RegisterUtil.getRegisterIndex((Integer) argument1.getValue())).thenReturn(7);

		when(arguments.get(1)).thenReturn(argument2);
		when(argument2.isRegister()).thenReturn(true);
		when(argument2.getValue()).thenReturn(0x1000103);

		when(registri.getRegisterValue(7)).thenReturn(10);
		when(registri.getRegisterValue(3)).thenReturn(10);
		
		when(memorija.getLocation(11)).thenReturn(5);
		
		Instruction move = new InstrMove(arguments);
		move.execute(comp);

		verify(registri,times(1)).getRegisterValue(7);
		verify(registri,times(1)).getRegisterValue(3);
		verify(memorija,times(1)).getLocation(11);
		verify(memorija, times(1)).setLocation(12, 5);
		verify(argument1, times(1)).getValue();
		verify(argument2, times(1)).getValue();

	}
}
