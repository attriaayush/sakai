package org.sakaiproject.profile2.tool.dataproviders;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.sakaiproject.profile2.logic.ProfileLogic;
import org.sakaiproject.profile2.model.Message;
import org.sakaiproject.profile2.tool.Locator;
import org.sakaiproject.profile2.tool.models.DetachableMessageModel;

/**
 * Implementation of IDataProvider that retrieves the latest message in each thread
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 * 
 */

public class MessageThreadHeadersDataProvider implements IDataProvider<Message> {
    
	private static final long serialVersionUID = 1L;
	private final String userUuid;
	
	public MessageThreadHeadersDataProvider(String userUuid) {
		this.userUuid = userUuid;
	}
	
	protected ProfileLogic getProfileLogic(){
		return Locator.getProfileLogic();
	}

	/**
	 * retrieves messages from database, gets the sublist and returns an iterator for that sublist
	 * 
	 * @see org.apache.wicket.markup.repeater.data.IDataProvider#iterator(int, int)
	 */
	public Iterator<Message> iterator(int first, int count){
		
		try {
			List<Message> slice = getProfileLogic().getMessageThreadHeaders(userUuid).subList(first, first + count);
			return slice.iterator();
		}
		catch (Exception e) {
			e.printStackTrace();
			return Collections.EMPTY_LIST.iterator();
		}
	}

	/**
	 * returns total number of message thread headers
	 * 
	 * @see org.apache.wicket.markup.repeater.data.IDataProvider#size()
	 */
	public int size(){
		return getProfileLogic().getMessageThreadHeadersCount(userUuid);
	}

	/**
	 * wraps retrieved message pojo with a wicket model
	 * 
	 * @see org.apache.wicket.markup.repeater.data.IDataProvider#model(java.lang.Object)
	 */
	public IModel<Message> model(Message object){
		return new DetachableMessageModel(object);
	}

	/**
	 * @see org.apache.wicket.model.IDetachable#detach()
	 */
	public void detach(){
	}
      
	

  }