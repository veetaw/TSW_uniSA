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
		                            <div class="mdc-card__ripple"></div>
		                        </div>
		                    </div>
		                    <div class="mdc-card__actions">
							  <button class="mdc-button mdc-card__action mdc-card__action--button mdc-button--icon-leading" onclick="edit_wine('<%= bean.getIdProdotto() %>')" style="position: absolute; right: 0;">
								  <span class="mdc-button__ripple"></span>
								  <i class="material-icons mdc-button__icon" aria-hidden="true" style="color: #550024">edit</i>
								  <span class="mdc-button__label" style="color: #550024">MODIFICA</span>
							  </button>
							</div>
							  <button class="mdc-button mdc-card__action mdc-card__action--button mdc-button--icon-leading" onclick="delete_wine('<%= bean.getIdProdotto() %>')" style="position: absolute; right: 0;">
								  <span class="mdc-button__ripple"></span>
								  <i class="material-icons mdc-button__icon" aria-hidden="true" style="color: #550024">delete</i>
							  </button>

		                </div>
		            </div>
 