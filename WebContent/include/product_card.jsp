			       	<div class="mdc-layout-grid__cell" onclick="window.location.href = 'details?id=<%=bean.getIdProdotto()%>'">
		                <div class="mdc-card">
		                    <div class="mdc-card__primary-action" tabindex="1" style="padding: 16px;">
		                        <div class="my-card__media mdc-card__media mdc-card__media--16-9" style="background-image: url('<%=bean.getUrl()%>'); background-size: contain;"></div>
		                        <div>
		                            <span class="mdc-typography--headline6"><%=bean.getNome()%></span>
		                            <br>
		                            <span class="mdc-typography--subtitle1"><%=bean.getDescrizione().substring(0, Math.min(bean.getDescrizione().length(), 120)) + "..."%></span>
		                            <div class="mdc-card__ripple"></div>
		                        </div>
		                    </div>
		                </div>
		            </div>
