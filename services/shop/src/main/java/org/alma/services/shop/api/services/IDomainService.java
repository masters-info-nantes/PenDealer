package org.alma.services.shop.api.services;

import org.alma.services.bank.domain.services.impl.BankStub;
import org.alma.services.bourse.domain.services.impl.BourseStub;
import org.alma.services.supplier.domain.services.impl.SupplierStub;
import org.alma.services.shop.domain.entities.CartItem;
import org.apache.axis2.AxisFault;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by jeremy on 23/11/15.
 */
public interface IDomainService {
    public String[] GetCurrencies() throws RemoteException;

    public SupplierStub.Product GetProduct(String productReference) throws AxisFault, RemoteException;
    public SupplierStub.Product[] GetProductsList() throws RemoteException;

    public boolean AddToCart(String productReference) throws RemoteException;
    public void RemoveFromCart(String productReference);
    public List<CartItem> GetCart(String currency) throws RemoteException;

    public boolean ProcessOrder() throws AxisFault, RemoteException;
}
