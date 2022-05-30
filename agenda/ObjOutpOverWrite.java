package agenda;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ObjOutpOverWrite extends ObjectOutputStream{

	public ObjOutpOverWrite(OutputStream out) throws IOException {
		super(out);
		
	}
	protected void writeStreamHeader() throws IOException {};
}
