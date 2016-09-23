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
package fr.paris.lutece.plugins.crm.modules.mydashboardfavorites.service;

import fr.paris.lutece.plugins.subscribe.business.Subscription;
import fr.paris.lutece.plugins.subscribe.service.ISubscriptionProviderService;
import fr.paris.lutece.portal.service.security.LuteceUser;
import java.util.Locale;

/**
 *
 * @author closea
 */
public class FavoritesSubscriptionProviderService implements ISubscriptionProviderService {
    
    private static final String FAVORITES_PROVIDER_NAME = "FAVORITES_PROVIDER";
    
    static FavoritesSubscriptionProviderService _instance = null;
    
    public static FavoritesSubscriptionProviderService getInstance(  ){
        if ( _instance == null ){
            _instance = new FavoritesSubscriptionProviderService(  );
            return _instance;
        }
        else return _instance;
    }
    
    
    @Override
    public String getProviderName( ){
        return FAVORITES_PROVIDER_NAME;
    };

    @Override
    public String getSubscriptionHtmlDescription( LuteceUser user, String strSubscriptionKey, String strIdSubscribedResource,
            Locale locale ){
        return "";
    }
    
    @Override
    public String getSubscriptionHtmlDescriptionBis( LuteceUser user, String strSubscriptionKey, String strIdSubscribedResource,
            Locale locale, String userSub ){
        return "";
    }
    
    @Override
    public boolean isSubscriptionRemovable( LuteceUser user, String strSubscriptionKey, String strIdSubscribedResource ){
        return false;
    }

    @Override
    public String getUrlModifySubscription( LuteceUser user, String strSubscriptionKey, String strIdSubscribedResource ){
        return null;
    }

    @Override
    public void notifySubscriptionRemoval( Subscription subscription ){
    };
}
