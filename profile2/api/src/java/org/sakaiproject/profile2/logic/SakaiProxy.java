/**
 * Copyright (c) 2008-2010 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sakaiproject.profile2.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sakaiproject.api.common.edu.person.SakaiPerson;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.user.api.User;
/**
 * An interface for abstracting Sakai specific parts away from the main logic.
 * 
 * @author Steve Swinsburg (s.swinsburg@lancaster.ac.uk)
 *
 */
public interface SakaiProxy {
	
	/**
	 * Get current siteid
	 * @return
	 */
	public String getCurrentSiteId();
	
	/**
	 * Get current user id
	 * @return
	 */
	public String getCurrentUserId();
	
	/**
	 * Get current user
	 * @return
	 */
	public User getCurrentUser();
	
	/**
	 * Convert internal userid to eid (jsmith26)
	 * @return
	 */
	public String getUserEid(String userId);
	
	/**
	 * Convert eid to internal userid
	 * @return
	 */
	public String getUserIdForEid(String eid);

	/**
	 * Get displayname of a given userid (internal id)
	 * @return
	 */
	public String getUserDisplayName(String userId);
	
	/**
	 * Get firstname of a given userid (internal id)
	 * @return
	 */
	public String getUserFirstName(String userId);
	
	/**
	 * Get lastname of a given userid (internal id)
	 * @return
	 */
	public String getUserLastName(String userId);
	
	/**
	 * Get email address for a given userid (internal id)
	 * @return
	 */
	public String getUserEmail(String userId);
	
	/**
	 * Check if a user with the given internal id (ie 6ec73d2a-b4d9-41d2-b049-24ea5da03fca) exists
	 * @param userId
	 * @return
	 */
	public boolean checkForUser(String userId);
	
	/**
	 * Check if a user with the given eid (ie jsmith26) exists
	 * @param userId
	 * @return
	 */
	public boolean checkForUserByEid(String eid);
	
	/**
	 * Is the current user a superUser? (anyone in admin realm)
	 * @return
	 */
	public boolean isSuperUser();
	
	/**
	 * Is the current user the admin user? (ie 'admin')
	 * @return
	 */
	public boolean isAdminUser();
	
	/**
	 * Is the current user a superUser and are they performing an action on another user's profile?
	 * @param userId - userId of other user
	 * @return
	 */
	public boolean isSuperUserAndProxiedToUser(String userId);
	/**
	 * Get the type of this user's account
	 * @param userId
	 * @return
	 */
	public String getUserType(String userId);
	
	/**
	 * Get a user
	 * @param userId internal user id
	 * @return
	 */
	public User getUserById(String userId);
	
	/**
	 * Get the User object for the given userId.
	 * <p>This will not log errors so that we can quietly use it to check if a User exists for a given profile,
	 *  ie in a search result, for example.</p>
	 * @param userId
	 * @return
	 */
	public User getUserQuietly(String userId);
	
	/**
	 * Get the title of the current tool
	 * @return
	 */
	public String getCurrentToolTitle();

	/**
	 * Get a list of Users for the given userIds
	 * @param userUuids		uuids
	 * @return List<User>
	 */
	public List<User> getUsers(List<String> userUuids);
	
	/**
	 * Get a list of uuids for each of the given Users
	 * @param users		Users
	 * @return List<String> of uuids
	 */
	public List<String> getUuids(List<User> users);
	
	/**
	 * Get a SakaiPerson for a user
	 * @param userId
	 * @return
	 */
	public SakaiPerson getSakaiPerson(String userId);
	
	/**
	 * Get a SakaiPerson Jpeg photo for a user. Checks UserMutableType record first, then goes for SystemMutableType if none.
	 * <p>Returns null if nothing found.
	 * @param userId
	 * @return
	 */
	public byte[] getSakaiPersonJpegPhoto(String userId);
	
	/**
	 * Get a SakaiPerson image URL for a user. Checks UserMutableType record first, then goes for SystemMutableType if none.
	 * <p>Returns null if nothing found.
	 * @param userId
	 * @return
	 */
	public String getSakaiPersonImageUrl(String userId);
	
	/**
	 * Get a SakaiPerson prototype if they don't have a profile.
	 * <p>This is not persistable so should only be used for temporary views.
	 * Use createSakaiPerson if need persistable object for saving a profile.
	 * @param userId
	 * @return
	 */
	public SakaiPerson getSakaiPersonPrototype();
	
	/**
	 * Create a new persistable SakaiPerson object for a user
	 * @param userId
	 * @return
	 */
	public SakaiPerson createSakaiPerson(String userId);

	/**
	 * Update a SakaiPerson object in the db
	 * @param sakaiPerson
	 * @return
	 */
	public boolean updateSakaiPerson(SakaiPerson sakaiPerson);
	
	/**
	 * Get the maximum filesize that can be uploaded (profile2.picture.max=2)
	 * @return
	 */
	public int getMaxProfilePictureSize();
		
	/**
	 * Returns the gallery resource path for the specified user and image.
	 * 
	 * @param userId the ID of the user.
	 * @param imageId the ID of the image.
	 * @return the gallery resource path for the specified user and image.
	 */
	public String getProfileGalleryImagePath(String userId, String imageId);
	
	/**
	 * Returns the gallery resource path for the specified user and thumbnail.
	 * 
	 * @param userId the ID of the user.
	 * @param imageId the ID of the thumbnail.
	 * @return the gallery resource path for the specified user and image.
	 */
	public String getProfileGalleryThumbnailPath(String userId, String thumbnailId);
	
	/**
	 * Get the location for a profileImage given the user and type
	 * 
	 * @param userId
	 * @param type
	 * @return
	 */
	public String getProfileImageResourcePath(String userId, int type);
	
	/**
	 * Save a file to CHS
	 * 
	 * @param fullResourceId
	 * @param userId
	 * @param fileName
	 * @param mimeType
	 * @param fileData
	 * @return
	 */
	public boolean saveFile(String fullResourceId, String userId, String fileName, String mimeType, byte[] fileData);
		
	/**
	 * Retrieve a resource from ContentHosting as a byte[]
	 *
	 * @param resourceId	the full resourceId of the file
	 */
	public byte[] getResource(String resourceId);
	
	
	/**
	 * Removes the specified resource.
	 * 
	 * @param resourceId the ID of the resource to remove.
	 * @return <code>true</code> if the resource is successfully removed,
	 * <code>false</code> if the remove operation fails. 
	 */
	public boolean removeResource(String resourceId);
	
	/**
	 * Search UserDirectoryService for users that match in name or email.
	 *
	 * @param search	search string.
	 * @return a List of User objects
	 */
	public List<User> searchUsers(String search);
	
	/**
	 * Search UserDirectoryService for externally provided users that match in name or email.
	 *
	 * @param search	search string.
	 * @return a List of User objects
	 */
	public List<User> searchExternalUsers(String search);
	
	
	/**
	 * Post an event to Sakai
	 * 
	 * @param event			name of event
	 * @param reference		reference
	 * @param modify		true if something changed, false if just access
	 * 
	 */
	public void postEvent(String event,String reference,boolean modify);

	/**
	 * Send an email message. The message should be ready to go as is. The message will be formatted with
	 * mime boundaries, html escaped then sent.
	 * 
	 * @param userId	userId to send the message to
	 * @param subject	subject of message
	 * @param message	complete with newlines and any links.
	 */
	public void sendEmail(String userId, String subject, String message);
	
	/**
	 * Sends an email to a list of users using the email template and replacement values supplied
	 * @param users			list of userIds to send to - already cleaned up for their email preferences
	 * @param emailTemplateKey	the email template
	 * @parem replacementValues	Map of values that are substituted into the placeholders in the email template.
	 */
	public void sendEmail(List<String> userIds, String emailTemplateKey, Map<String,String> replacementValues);
	
	/**
	 * Sends an email to a single user using the email template and replacement values supplied
	 * @param user			userId to send to - already cleaned up for their email preferences
	 * @param emailTemplateKey	the email template
	 * @parem replacementValues	Map of values that are substituted into the placeholders in the email template.
	 */
	public void sendEmail(String userId, String emailTemplateKey, Map<String,String> replacementValues);

	
	/**
	 * Get the name of this Sakai installation (ie Sakai@Lancs)
	 * @return
	 */
	public String getServiceName();
	
	/**
	 * Gets the portalUrl configuration parameter (ie http://sakai.lancs.ac.uk/portal)
	 * Will not work outside of /portal context (ie won't work from an entityprovider)
	 * @return
	 */
	public String getPortalUrl();
	
	/**
	 * Gets the serverUrl configuration parameter (http://sakai.lancs.ac.uk:8080)
	 * @return
	 */
	public String getServerUrl();
	
	/**
	 * Get the DNS name of this Sakai server (ie sakai.lancs.ac.uk)
	 * @return
	 */
	public String getServerName();
	
	/**
	 * Get the URL to the current user's my workspace
	 * @return
	 */
	public String getUserHomeUrl();
	
	/**
	 * Gets the portal path, generally /portal unless override in sakai.properties
	 * @return
	 */
	public String getPortalPath();
	
	/**
	 * Are we using the normal /portal? 
	 * Deep links are broken in xsl-portal, so we need a workaround to drop the toolstate param.
	 * See PRFL-264
	 * @return
	 */
	public boolean isUsingNormalPortal();
	
	/**
	 * Gets the full portal url by adding getServerUrl() and getPortalPath() together
	 * This WILL work outside the portal context so safe to use from an entityprovider
	 * @return
	 */
	public String getFullPortalUrl();
	
	/**
	 * Updates a user's email address
	 * If the user is a provided user (ie from LDAP) this will probably fail
	 * so only internal accounts can be updated.
	 * 
	 * @param userId	uuid of the user
	 * @param email	
	 */
	public void updateEmailForUser(String userId, String email);
	
	/**
	 * Updates a user's name
	 * If the user is a provided user (ie from LDAP) this will probably fail
	 * so only internal accounts can be updated.
	 * 
	 * @param userId	uuid of the user
	 * @param email	
	 */
	public void updateNameForUser(String userId, String firstName, String lastName);
	
	
	/**
	 * Creates a direct URL to a user's profile page on their My Workspace
	 * Any other parameters supplied in string are appended and encoded.
	 * @param userId		uuid of the user
	 * @param extraParams	any extra params to add to the query string
	 * @return
	 */
	public String getDirectUrlToUserProfile(String userId, String extraParams);
	
	/**
	 * Check if a user is allowed to update their account. The User could come from LDAP
	 * so updates not allowed. This will check if any updates are allowed.
	 * 
	 * Note userDirectoryService.allowUpdateUserEmail etc are NOT the right methods to use
	 * as they don't check if account updates are allowed, just if the user doing the update is allowed.
	 * 
	 * @param userId
	 * @return
	 */
	public boolean isAccountUpdateAllowed(String userId);
	
	/**
	 * Is the profile2.profile.business.enabled flag set in sakai.properties? If
	 * not set, defaults to <code>false</code>.
	 * 
	 * @return <code>true</code> if the profile2.profile.business.enabled flag
	 *         is set, otherwise returns <code>false</code>.
	 */
	public boolean isBusinessProfileEnabled();
	
	/**
	 * Is the profile2.convert flag set in sakai.properties?
	 * If not set, defaults to false
	 * 
	 * <p>This will convert profiles from the original Profile tool in Sakai, to Profile2 format. 
	 * Any images that were uploaded via various methods will be placed into Sakai's ContentHostingSystem
	 * and thumbnails generated for use in various parts of Profile2.</p>
	 * 
	 * @return
	 */
	public boolean isProfileConversionEnabled();
	
	
	/**
	 * Is the profile2.integration.twitter.enabled flag set in sakai.properties?
	 * If not set, defaults to true
	 * 
	 * <p>Depending on this setting, the UI will allow a user to input their Twitter settings
	 * and their status updates will be sent to Twitter.</p>
	 * 
	 * @return
	 */
	public boolean isTwitterIntegrationEnabledGlobally();
	
	/**
	 * 
	 * Get the profile2.integration.twitter.source parameter
	 *
	 * See here:
	 * http://bugs.sakaiproject.org/confluence/display/PROFILE/Profile2
	 */
	public String getTwitterSource();
	
	/**
	 * Is the profile2.gallery.enabled flag set in sakai.properties?
	 * If not set, default to <code>true</code>.
	 * 
	 * @return the status of the profile2.gallery.enabled flag in sakai.properties.
	 * Returns <code>true</code> by default.
	 */
	public boolean isProfileGalleryEnabledGlobally();
	
	/**
	 * Is the profile2.picture.change.enabled flag set in sakai.properties?
	 * If not set, defaults to true
	 * 
	 * <p>Depending on this setting, users will be able to upload their own image. 
	 * This can be useful to disable for institutions which may provide official photos for students.</p>
	 * 
	 * @return
	 */
	public boolean isProfilePictureChangeEnabled();
	
	/**
	 * Get the profile2.picture.type setting in sakai.properties
	 * <p>Possible values for the sakai property are 'upload', 'url', and 'official'.
	 * If not set, defaults to 'upload'.</p>
	 * <p>This returns an int which matches one of: ProfileConstants.PICTURE_SETTING_UPLOAD, ProfileConstants.PICTURE_SETTING_URL, ProfileConstants.PICTURE_SETTING_OFFICIAL</p>
	 * 
	 * <p>Depending on this setting, Profile2 will decide how it retrieves a user's profile image, and the method by which
	 * users can add their own image. ie by uploading their own image, providing a URL, or not at all (for official).</p>
	 * 
	 * @return
	 */
	public int getProfilePictureType();
	
	
	
	/**
	 * Gets the profile2.profile.entity.set.academic list of properties that should be used in the academic profile view.
	 * Returns default list of ProfileConstants.ENTITY_SET_ACADEMIC if none.
	 * @return
	 */
	public List<String> getAcademicEntityConfigurationSet();
	
	/**
	 * Gets the profile2.profile.entity.set.minimal list of properties that should be used in the minimal profile view.
	 * Returns default list of ProfileConstants.ENTITY_SET_MINIMAL if none.
	 * @return
	 */
	public List<String> getMinimalEntityConfigurationSet();

	/**
	 * Convenience method to ensure the given userId(eid or internal id) is returned as a valid uuid.
	 * 
	 * <p>External integrations must pass input through this method, where the input can be either form,
	 * since all data is keyed on the internal user id.
	 * 
	 * @param userId
	 * @return uuid or null
	 */
	public String ensureUuid(String userId);
	
	/**
	 * Is the profile2.privacy.change.enabled flag set in sakai.properties?
	 * If not set, defaults to true
	 * <p>
	 * 
	 * Allows an institution to lock down privacy changes as some things need to be never changed.
	 * Generally should coupled with the sakai.properties that override the default privacy settings
	 * 
	 * @return
	 */
	public boolean isPrivacyChangeAllowedGlobally();
	
	
	/**
	 * Gets the set of sakai.properties that can override the built in defaults. This is then called
	 * when a default privacy record is requested and the values used preferentially.
	 * 
	 * Note that mostly the value is an Integer but for some values its a Boolean, ie checkboxes
	 * 
	 * @return
	 */
	public HashMap<String, Object> getOverriddenPrivacySettings();
	
	/**
	 * Gets the profile2.invisible.users List for user's that should never show in searches or connection lists
	 * @return
	 */
	public List<String> getInvisibleUsers();
	
	/**
	 * Gets the list of usertypes from profile2.allowed.connection.usertypes.TYPE.
	 * <p>This defines what user types can connect to each other. The <b>requestingUserType</b> is appended to the property string
	 * and the list retrieved. If targetUserType is in the list, they can connect (true). If not, false.</p>
	 * <p>If the property does not exist for that userType, they can connect, to maintain backward compatibility and keep it open.</p>
	 *
	 * @param requestingUserType
	 * @param targetUserType
	 * @return
	 */
	public boolean isConnectionAllowedBetweenUserTypes(String requestingUserType, String targetUserType);
	
	/**
	 * Toggle a profile's locked status.
	 * @param userId
	 * @param locked
	 * @return
	 */
	public boolean toggleProfileLocked(String userId, boolean locked);
	
	/**
	 * Is profile2.official.image.enabled true? If so, allow use of this image and preference.
	 * @return
	 */
	public boolean isOfficialImageEnabledGlobally();
	
	/**
	 * Checks if the conditions are appropriate for a user to be able to select whether to use the official
	 * image or an alternate of their choice
	 * @return
	 */
	public boolean isUsingOfficialImageButAlternateSelectionEnabled();
	
	/**
	 * Gets the value of the profile2.official.image.source attribute from sakai.properties.
	 * If not set, defaults to ProfileConstants.OFFICIAL_SETTING_DEFAULT
	 * 
	 * This should be specified if profile2.picture.type=official
	 * 
	 * @return
	 */
	public String getOfficialImageSource();
	
	/**
	 * Gets the value of the profile2.official.image.attribute from sakai.properties
	 * If not set, defaults to ProfileConstants.USER_PROPERTY_JPEG_PHOTO
	 * 
	 * This should be specified if profile2.official.image.source=provided
	 *
	 * @return
	 */
	public String getOfficialImageAttribute();
	
	/**
	 * Wrapper for ServerConfigurationService.getString("skin.repo")
	 * @return
	 */
	public String getSkinRepoProperty();
	
	/**
	 * Gets the tool skin CSS first by checking the tool, otherwise by using the default property.
	 * @param	the location of the skin repo
	 * @return
	 */
	public String getToolSkinCSS(String skinRepo);
	
	/**
	 * Get a UUID from the IdManager
	 * @return
	 */
	public String createUuid();
	
	/**
	 * Does the user have a current Sakai session
	 * @param userUuid	user to check
	 * @return	true/false
	 */ 
	public boolean isUserActive(String userUuid);
	
	/**
	 * Get the list of users with active Sakai sessions, given the supplied list of userIds.
	 * @param userUuids		List of users to check
	 * @return	List of userUuids that have active Sakai sessions
	 */
	public List<String> getActiveUsers(List<String> userUuids);
	
	/**
	 * Get the last event time for the user.
	 * @param userUuid		user to check
	 * @return	Long of time in milliseconds since epoch, or null if no event.
	 */
	public Long getLastEventTimeForUser(String userUuid);
	
	/**
	 * Get the last event time for each of the given users
	 * @param userUuids		users to check
	 * @return	Map of userId to Long of time in milliseconds since epoch, or null if no event.
	 */
	public Map<String, Long> getLastEventTimeForUsers(List<String> userUuids);
	
	
	
	
}
