/*
 * Copyright (c) 2002-2015, Mairie de Paris
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

import fr.paris.lutece.plugins.crm.business.demand.DemandType;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import fr.paris.lutece.plugins.crm.business.user.CRMUser;
import fr.paris.lutece.plugins.crm.modules.mydashboardfavorites.service.FavoritesSubscriptionProviderService;
import fr.paris.lutece.plugins.crm.service.demand.DemandTypeService;
import fr.paris.lutece.plugins.crm.service.user.CRMUserAttributesService;
import fr.paris.lutece.plugins.crm.service.user.CRMUserService;
import fr.paris.lutece.plugins.mydashboard.service.MyDashboardComponent;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.web.l10n.LocaleService;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.plugins.subscribe.business.SubscriptionFilter;
import fr.paris.lutece.plugins.subscribe.business.Subscription;
import fr.paris.lutece.plugins.subscribe.service.SubscriptionService;
import java.util.ArrayList;

public class MyDashboardFavoritesComponent extends MyDashboardComponent
{
    private static final String DASHBOARD_COMPONENT_ID = "crm-mydashboard-favorites.favoritesComponent";
    private static final String MESSAGE_DASHBOARD_COMPONENT_DESCRIPTION = "module.crm.mydashboard.favorites.component.description";
    private static final String TEMPLATE_DASHBOARD_COMPONENT = "skin/plugins/crm/modules/mydashboard/favorites/favorites_component.html";
    
    //Subscription providers 

    //Markers
    private static final String MARK_FAVORITES_CHECKED_LIST = "favorites_checked_list";
    private static final String MARK_FAVORITES_LIST = "favorites_list";

    @Override
    public String getDashboardData( HttpServletRequest request )  
    {
        LuteceUser user = SecurityService.getInstance(  ).getRegisteredUser( request );
        CRMUser crmUser = CRMUserService.getService(  ).findByUserGuid( user.getName(  ) );
        Map<String, Object> model = new HashMap<String, Object>(  );
        
        //research by filter on user guid and for favorites provider
        SubscriptionFilter sFilter = new SubscriptionFilter(  );
        sFilter.setIdSubscriber( crmUser.getUserGuid(  ) );
        sFilter.setSubscriptionProvider( FavoritesSubscriptionProviderService.getInstance(  ).getProviderName(  ) );
        List<Subscription> listFavorites = SubscriptionService.getInstance().findByFilter( sFilter );  
        //get demandTypes about those subscriptions
        List<DemandType> listFavoritesSuscribed = new ArrayList(  );
        for ( Subscription sub : listFavorites )
        {
            listFavoritesSuscribed.add( DemandTypeService.getService(  ).findByPrimaryKey( Integer.parseInt( sub.getIdSubscribedResource(  ) ) ) );
        }

        model.put( MARK_FAVORITES_CHECKED_LIST, listFavoritesSuscribed );
        model.put( MARK_FAVORITES_LIST , DemandTypeService.getService(  ).findDemandTypes(  ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_DASHBOARD_COMPONENT, LocaleService.getDefault(  ), model );

        return template.getHtml(  );
    }
    


    @Override
    public String getComponentId(  )
    {
        return DASHBOARD_COMPONENT_ID;
    }

    @Override
    public String getComponentDescription( Locale locale )
    {
        return I18nService.getLocalizedString( MESSAGE_DASHBOARD_COMPONENT_DESCRIPTION, locale );
    }

}
