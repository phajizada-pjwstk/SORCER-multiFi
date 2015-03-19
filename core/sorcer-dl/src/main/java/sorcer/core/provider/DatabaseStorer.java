/*
 * Copyright 2012 the original author or authors.
 * Copyright 2012 SorcerSoft.org.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sorcer.core.provider;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Remote;
import java.rmi.RemoteException;

import sorcer.core.provider.StorageManagement;
import sorcer.core.provider.DatabaseStorer.Store;
import net.jini.id.Uuid;


public interface DatabaseStorer extends StorageManagement, Remote { 
	
	public enum Store { context, exertion, var, varmodel, table, object, all }
	
	public Uuid store(Object object) throws RemoteException;
	
	public URL getDatabaseURL(Store storeType, Uuid uuid) throws MalformedURLException, RemoteException;
}