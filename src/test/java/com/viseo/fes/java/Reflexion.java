package com.viseo.fes.java;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.util.*;

public class Reflexion {

    private final static Comparator<URL> URL_COMPARATOR = Comparator.comparing(String::valueOf);

	@Test
	void foo() {
		Map<URL, String> map = new TreeMap<URL, String>(URL_COMPARATOR);
		File file = null;
		String pathSep = System.getProperty ("path.separator");
		String classpath = System.getProperty ("java.class.path");
		StringTokenizer st = new StringTokenizer (classpath, pathSep);
        while (st.hasMoreTokens ())
        {
            String path = st.nextToken ();
            file = new File(path);
//            include (null, file, map);
        }

        Iterator<URL> it = map.keySet ().iterator ();
        while (it.hasNext ())
        {
            URL url = it.next ();
            System.out.println (url + "-->" + map.get (url));
        }
//		Thread.currentThread().getContextClassLoader();
	}
}
