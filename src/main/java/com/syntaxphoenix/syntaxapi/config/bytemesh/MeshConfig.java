package com.syntaxphoenix.syntaxapi.config.bytemesh;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.syntaxphoenix.syntaxapi.config.BaseConfig;

/**
 * @author Lauriichen
 *
 */
public class MeshConfig extends MeshConfigSection implements BaseConfig {
	
	

	/**
	 * @see com.syntaxphoenix.syntaxapi.config.BaseConfig#load(java.io.File)
	 */
	@Override
	public void load(File file) throws IOException {

		if (file.exists()) {
			
			DataInputStream input = new DataInputStream(new FileInputStream(file));
			
			byte[] output = new byte[0];
			input.readFully(output);
			input.close();
			
			fromMeshedData(output);
			
		}
		
	}

	/**
	 * @see com.syntaxphoenix.syntaxapi.config.BaseConfig#save(java.io.File)
	 */
	@Override
	public void save(File file) throws IOException {
		
		DataOutputStream output = new DataOutputStream(new FileOutputStream(file));
		
		output.write(toMeshedData());
		
		output.flush();
		output.close();
		
	}

}
