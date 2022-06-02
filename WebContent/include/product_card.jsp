<%@ page language="java" contentType="text/html; 
	charset=UTF-8"
	pageEncoding="UTF-8"%>


			       	<div class="mdc-layout-grid__cell">
		                <div class="mdc-card">
		                    <div class="mdc-card__primary-action" tabindex="1" style="padding: 16px;" onclick="window.location.href = 'details?id=<%=bean.getIdProdotto()%>'">
		                        <div class="my-card__media mdc-card__media mdc-card__media--16-9" style="background-image: url('<%=bean.getUrl()%>'); background-size: contain;"></div>
		                        <div>
		                            <span class="mdc-typography--headline6"><%=bean.getNome()%></span>
		                            <br>
		                            <span class="mdc-typography--subtitle1"><%=bean.getDescrizione().substring(0, Math.min(bean.getDescrizione().length(), 120)) + "..."%></span>
		                            <div class="mdc-card__ripple"></div>
		                        </div>
		                    </div>
		                    <div class="mdc-card__actions">
		                      <span class="mdc-typography--body1" style="font-weight: bold;"><%= String.format("%.2f", bean.getPrezzo()) %> €</span>
							  <button class="mdc-button mdc-card__action mdc-card__action--button mdc-button--icon-leading" onclick="window.location.href = 'product?action=addC&id=<%=bean.getIdProdotto()%>'" style="position: absolute; right: 0;">
								  <span class="mdc-button__ripple"></span>
								  <i class="material-icons mdc-button__icon" aria-hidden="true" style="color: #550024">add_shopping_cart</i>
								  <span class="mdc-button__label" style="color: #550024">Aggiungi al carrello</span>
							  </button>
							  
							</div>
		                </div>
		            </div>
 