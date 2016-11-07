/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.crm.modules.mydashboardfavorites.listener;

import fr.paris.lutece.plugins.crm.business.demand.DemandType;
import fr.paris.lutece.plugins.crm.modules.mydashboardfavorites.service.FavoritesSubscriptionProviderService;
import fr.paris.lutece.plugins.crm.util.IListenerDemandType;
import fr.paris.lutece.plugins.crm.util.constants.CRMConstants;
import fr.paris.lutece.plugins.subscribe.business.Subscription;
import fr.paris.lutece.plugins.subscribe.business.SubscriptionFilter;
import fr.paris.lutece.plugins.subscribe.service.SubscriptionService;
import fr.paris.lutece.portal.service.util.AppLogService;
import java.util.List;

/**
 * Listener for removal of favorites when a crm demand type is deleted
 */

public class ListenerFavoritesRemoval implements IListenerDemandType {
        
    @Override 
    public void notifyListener(DemandType demandType,String strEventName){
    
    if ( strEventName.equals( CRMConstants.EVENT_CRM_DEMAND_TYPE_REMOVED ) )
        if ( demandType != null ){
            int nIdDemandType = demandType.getIdDemandType(  );
            SubscriptionFilter sFilter = new SubscriptionFilter(  );
            sFilter.setIdSubscribedResource( Integer.toString( nIdDemandType ) );
            sFilter.setSubscriptionProvider( FavoritesSubscriptionProviderService.getInstance(  ).getProviderName(  ) );
            List<Subscription> listSubscriptionFavorites = SubscriptionService.getInstance(  ).findByFilter( sFilter );
            AppLogService.debug( "Deletion of all favorites attached to CRM demand type " + nIdDemandType );
            for (Subscription sub : listSubscriptionFavorites)
            {
                SubscriptionService.getInstance(  ).removeSubscription( sub, false);
            }
        }
    }
}
