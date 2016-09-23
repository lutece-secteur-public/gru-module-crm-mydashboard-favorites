/*
 * Copyright (c) 2002-2014, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.crm.modules.mydashboardfavorites.web;

import fr.paris.lutece.plugins.crm.business.user.CRMUser;
import fr.paris.lutece.plugins.crm.modules.mydashboardfavorites.service.FavoritesSubscriptionProviderService;
import fr.paris.lutece.plugins.crm.service.user.CRMUserService;
import fr.paris.lutece.plugins.subscribe.business.Subscription;
import fr.paris.lutece.plugins.subscribe.business.SubscriptionFilter;
import fr.paris.lutece.plugins.subscribe.service.SubscriptionService;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.xpage.MVCApplication;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.xpages.XPage;
import java.util.List;
import javax.servlet.http.HttpServletRequest;


@Controller( xpageName = "favorites" )
public class MyDashBoardFavoritesXPage extends MVCApplication 
{
    
    //Actions
    private static final String ACTION_MODIFY_FAVORITES = "modify_favorites";
    
    //Redirections
    private static final String REDIRECT_DASHBOARD = "Portal.jsp?page=mydashboard&panel=accueil";
    
    //Subscription key for favorites
    private static final String SUBSCRIPTION_KEY = "favorite_key";
    
    @Action( ACTION_MODIFY_FAVORITES )
    public XPage modifyFavorites( HttpServletRequest request )
    {
        String[] listFavoritesCheckedId = request.getParameterValues( "favorites" );
        
        //First remove all the favorites subscriptions for the register Lutece user
        LuteceUser user = SecurityService.getInstance().getRegisteredUser( request );
        CRMUser crmUser = CRMUserService.getService(  ).findByUserGuid( user.getName(  ) );
        SubscriptionFilter sFilter = new SubscriptionFilter(  );
        sFilter.setIdSubscriber( crmUser.getUserGuid(  ) );
        sFilter.setSubscriptionProvider( FavoritesSubscriptionProviderService.getInstance(  ).getProviderName(  ) );
        List<Subscription> listSubscriptionFavorites = SubscriptionService.getInstance(  ).findByFilter( sFilter ); 
        for (Subscription sub : listSubscriptionFavorites)
        {
            SubscriptionService.getInstance(  ).removeSubscription( sub, false);
        }
        
        //Then subscribe new favorites
        if ( listFavoritesCheckedId != null )
        {
            for (String strFavoriteId : listFavoritesCheckedId)
            {
                Subscription sub = new Subscription(  );
                sub.setIdSubscribedResource( strFavoriteId );
                sub.setSubscriptionProvider( FavoritesSubscriptionProviderService.getInstance(  ).getProviderName(  ) );
                sub.setSubscriptionKey( SUBSCRIPTION_KEY );
                SubscriptionService.getInstance().createSubscription( sub, user );
            }
        }
        
        return redirect( request, REDIRECT_DASHBOARD );
    }
}
