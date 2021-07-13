package com.lduran.algafood.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

@Component
public class ResourceUtils
{

	public static String getContentFromResource(String resourceName)
	{
		try
		{
			InputStream stream = ResourceUtils.class.getResourceAsStream(resourceName);
			return StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

}