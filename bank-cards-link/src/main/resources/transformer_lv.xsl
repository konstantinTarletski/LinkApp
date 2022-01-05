<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template name="set_chip_data">
    <xsl:param name="bin"/>
  <xsl:param name="auth"/> 
    <xsl:element name="application_id">
        <xsl:choose>
          <!-- LV -->
          <xsl:when test="$bin = '442752'">216</xsl:when>
          <xsl:when test="$bin = '465228'">216</xsl:when>
          <xsl:when test="$bin = '477573'">216</xsl:when>
          <xsl:when test="$bin = '492175'">216</xsl:when>
          <xsl:when test="$bin = '489994'">216</xsl:when>
          <xsl:when test="$bin = '492176'">216</xsl:when>
          <xsl:when test="$bin = '499962'">216</xsl:when>
          <xsl:when test="$bin = '478585'">216</xsl:when>
          <!-- EE -->
          <xsl:when test="$bin = '408882'">216</xsl:when>
          <xsl:when test="$bin = '408883'">216</xsl:when>
          <xsl:when test="$bin = '408884'">216</xsl:when>
          <xsl:when test="$bin = '408885'">216</xsl:when>
          <xsl:when test="$bin = '456946'">216</xsl:when>
          <xsl:when test="$bin = '478590'">216</xsl:when>
          <xsl:when test="$bin = '478591'">216</xsl:when>
        </xsl:choose>
    </xsl:element>  
</xsl:template>

<xsl:template match="/">
  <xsl:element name="batchOfOrders">
<!-- bank_c specified in document -->
    <xsl:attribute name="bank_c">
        <xsl:value-of select="child::DOCUMENT/@BANK"/>
    </xsl:attribute>
<!-- main iterator -->
    <xsl:for-each select="DOCUMENT/ORDER">
        <xsl:element name="order">

            <!-- action: cardCreate|cardRenew|cardDuplicate -->
            <xsl:variable name="specialCard" select="substring(card,1,7)='4652281' and @TYPE='RENEW' and (crd_reason='KARTE BOJĀTA' or crd_reason='THE CARD HAS BEEN DAMAGED' or crd_reason='AIZMIRSU PIN KODU' or crd_reason='FORGOT PIN' or crd_reason='KARTE NOZAUDĒTA' or crd_reason='CARD IS LOST' or crd_reason='KARTE NOZAGTA' or crd_reason='CARD STOLEN' or crd_reason='PALIKUSI BANKOMĀTĀ' or crd_reason='FORGOTTEN AT ATM' or crd_reason='CITS IEMESLS' or crd_reason='OTHER CAUSE' or crd_reason='UZVĀRDA MAIŅA' or crd_reason='SURNAME CHANGE')"/>
            <xsl:attribute name="action">
                <xsl:choose>
                    <!-- new cards -->
                    <xsl:when test='@TYPE="NEW"'>cardCreate</xsl:when>
                    <!-- cards renew -->
                    <!--Old cards with auth_limit = 1 must have the auth_limit changed, but it can't be done via CMS API-->
                    <xsl:when test='@TYPE="RENEW" and (crd_reason="BEIDZIES TERMIŅŠ" or crd_reason="EXPIRED")'>cardRenew</xsl:when>
                    <xsl:when test='@TYPE="RENEW" and (crd_reason="KARTE BOJĀTA" or crd_reason="THE CARD HAS BEEN DAMAGED") and substring(card,1,7)!="4652281"'>cardReplace</xsl:when>
                    <xsl:when test='@TYPE="RENEW" and (crd_reason="AIZMIRSU PIN KODU" or crd_reason="FORGOT PIN") and substring(card,1,7)!="4652281"'>cardReplace</xsl:when>
                    <xsl:when test='@TYPE="RENEW" and (crd_reason="KARTE NOZAUDĒTA" or crd_reason="CARD IS LOST") and substring(card,1,7)!="4652281"'>cardReplace</xsl:when>
                    <xsl:when test='@TYPE="RENEW" and (crd_reason="KARTE NOZAGTA" or crd_reason="CARD STOLEN") and substring(card,1,7)!="4652281"'>cardReplace</xsl:when>
                    <xsl:when test='@TYPE="RENEW" and (crd_reason="PALIKUSI BANKOMĀTĀ" or crd_reason="FORGOTTEN AT ATM") and substring(card,1,7)!="4652281"'>cardReplace</xsl:when>
                    <xsl:when test='@TYPE="RENEW" and (crd_reason="CITS IEMESLS" or crd_reason="OTHER CAUSE") and substring(card,1,7)!="4652281"'>cardReplace</xsl:when>
                    <xsl:when test='@TYPE="RENEW" and (crd_reason="UZVĀRDA MAIŅA" or crd_reason="SURNAME CHANGE") and substring(card,1,7)!="4652281"'>cardReplace</xsl:when>
                    <xsl:when test='$specialCard'>cardCreate</xsl:when>
                    <xsl:otherwise>?</xsl:otherwise>
                </xsl:choose>               
            </xsl:attribute>

            <xsl:element name="card">
                <xsl:choose>
                    <xsl:when test='$specialCard'>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="card"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:element>
            
            <!-- If we change cardReplace action with cardCreate we need to block old card  -->
            <xsl:if test="$specialCard">
                <xsl:element name="block_card_no">
                    <xsl:value-of select="card"/>
                </xsl:element>
            </xsl:if>
            
    <!-- what we need to transfer, as is -->
            <xsl:for-each select="crd_email|crd_passwd|account_no|account_ccy|cl_code|crd_name|own_phone|own_passport|own_phone|dlv_address|dlv_addr_code|dlv_addr_country|dlv_addr_city|dlv_addr_street1|dlv_addr_street2|dlv_addr_zip|dlv_company|repeated|min_salary_account|country|migrated_card_number|migrated_card_pin_block|migrated_card_pin_key_id|risk_level">
                    <xsl:copy-of select="."/>
                <xsl:text>
                </xsl:text>
            
            </xsl:for-each>
    <!-- as a special case, we need to change card condition from "old" 0xx to "new" nxx -->
            <xsl:if test='@TYPE="RENEW" and (crd_reason="BEIDZIES TERMIŅŠ" or crd_reason="EXPIRED")'>
                <xsl:element name="prettyfyCard"/>
            </xsl:if>
            

     <!-- Add leading zeroes to 
 
        this will be decoded against NORDLB_BRANCHES and placed to
        IZD_CARDS.U_COD10
     -->
            <xsl:element name="crd_place">
                <xsl:choose>
                    <xsl:when test="crd_place='888'">888</xsl:when>
                    <xsl:otherwise><xsl:value-of select="substring(concat('00',crd_place),string-length(crd_place)+1)"/></xsl:otherwise>
                </xsl:choose>
            </xsl:element>

    <!--
     Create a "crd_auto_block" element to hard-block this card automatically before replacement.
     The value is taken from IZD_STOP_CAUSES.
    -->     
    <xsl:if test='@TYPE="RENEW"'>
            <xsl:element name="crd_auto_block">
                <xsl:choose>
                    <xsl:when test='crd_reason="KARTE NOZAGTA" or crd_reason="CARD STOLEN"'>1</xsl:when>
                    <xsl:when test='crd_reason="KARTE NOZAUDĒTA" or crd_reason="CARD IS LOST"'>2</xsl:when>
                    <xsl:when test='crd_reason="PALIKUSI BANKOMĀTĀ" or crd_reason="FORGOTTEN AT ATM"'>2</xsl:when>
                    <xsl:when test='crd_reason="KARTE BOJĀTA" or crd_reason="THE CARD HAS BEEN DAMAGED"'>2</xsl:when>
                    <xsl:when test='crd_reason="AIZMIRSU PIN KODU" or crd_reason="FORGOT PIN"'>2</xsl:when>
                    <xsl:when test='crd_reason="CITS IEMESLS" or crd_reason="OTHER CAUSE"'>2</xsl:when>
                    <xsl:when test='crd_reason="UZVĀRDA MAIŅA" or crd_reason="SURNAME CHANGE"'>2</xsl:when>
                    <xsl:otherwise></xsl:otherwise>
                </xsl:choose>
            </xsl:element>
    </xsl:if>
    
    <!-- Replace card name with new -->
    <xsl:if test='crd_reason="UZVĀRDA MAIŅA" or crd_reason="SURNAME CHANGE"'>
        <xsl:element name="replace_name">true</xsl:element>
    </xsl:if>
        
    <!-- Set up SMS directions -->
    <xsl:choose>
        <xsl:when test="crd_notify != ''">
            <xsl:element name="crd_notify">
                <xsl:value-of select="concat(concat(substring(crd_sms_templ,1,1), ':'),crd_notify)"/>
            </xsl:element>
        </xsl:when>
        <xsl:when test="crd_notify = ''">
            <xsl:element name="crd_notify"/>
        </xsl:when>
    </xsl:choose>
    <!-- Birthday -->
            
    <xsl:element name="birthday_mask">ddMMyyyy</xsl:element>
 
              <xsl:element name="cl_birthday">
                <xsl:if test='string-length(substring-before(cl_code,"-")) = 6'>
                    <xsl:choose>
                        <xsl:when test='number(substring(cl_code,5,2)) &lt; 25'>
                            <xsl:value-of select="concat(substring(cl_code,1,4),'20',substring(cl_code,5,2))" />
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="concat(substring(cl_code,1,4),'19',substring(cl_code,5,2))" />
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:if>
            </xsl:element>
                        
        <xsl:element name="own_birthday">
                <xsl:choose>
                    <xsl:when test='crd_sort="JURIDISKĀ" or crd_sort="LEGAL"'>
                        <xsl:if test='string-length(substring-before(cl_code,"-")) = 6'>
                            <xsl:choose>
                                <xsl:when test='number(substring(cl_code,5,2)) &lt; 25'>
                                    <xsl:value-of select="concat(substring(cl_code,1,4),'20',substring(cl_code,5,2))" />
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="concat(substring(cl_code,1,4),'19',substring(cl_code,5,2))" />
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:if>
                    </xsl:when>
                    <xsl:otherwise>
                        <!-- here should go checking if this is current century or not :) -->
                        <xsl:if test='string-length(substring-before(own_code,"-")) = 6'>
                            <xsl:choose>
                                <xsl:when test='number(substring(own_code,5,2)) &lt; 25'>
                                    <xsl:value-of select="concat(substring(own_code,1,4),'20',substring(own_code,5,2))" />
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="concat(substring(own_code,1,4),'19',substring(own_code,5,2))" />
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:if>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:element>
            
    <!-- ID -->
        <xsl:element name="orderId"><xsl:value-of select="NODE"/></xsl:element>
    <!-- Owner name -->
        <xsl:element name="own_name"><xsl:value-of select="translate(own_name,'&quot;','')"/></xsl:element>     
    <!-- Owner code -->
        <xsl:choose>
            <xsl:when test='crd_sort="JURIDISKĀ" or crd_sort="LEGAL"'>
                <xsl:element name="own_code"><xsl:value-of select="cl_code"/></xsl:element>
            </xsl:when>
            <xsl:otherwise>
                <xsl:element name="own_code"><xsl:value-of select="own_code"/></xsl:element>
            </xsl:otherwise>
        </xsl:choose>       
    <!-- BRANCH -->
            <xsl:element name="branch"><xsl:value-of select="substring-after(substring-before(translate(fill_place,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),','),'rkb')"/></xsl:element>
    <!-- Norekinu grupa where this was prepared 
        this will be placed to IZD_AGREEMENT.u_cid4
    -->
            <xsl:element name="fill_place_ng"><xsl:value-of select="substring(concat('000',substring-before(substring-after(fill_place,','),',')),string-length(substring-before(substring-after(fill_place,','),','))+1)"/></xsl:element>

    <!-- CIF should be uppercased ! -->         
            <xsl:element name="account_cif"><xsl:value-of select="translate(account_cif,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/></xsl:element>

    <!-- client since-->
    
          <!-- DD/MM/YYYY => YYMM -->
<!--            <xsl:element name="own_since"><xsl:value-of select="concat(substring(CL_BANK_DATE,string-length(CL_BANK_DATE)-1),substring-before(substring-after(CL_BANK_DATE,'/'),'/'))" /></xsl:element>-->

            <!-- DD/MM/YYYY => YYYY -->
            <xsl:element name="own_since">
                <xsl:value-of select="substring(CL_BANK_DATE, string-length(CL_BANK_DATE)-3, string-length(CL_BANK_DATE)-1)" />
            </xsl:element>

    <!-- Add 'F' to account_no -->
<!-- <xsl:choose>
        <xsl:when test='crd_reason="ChangeMaestro"'>
            <xsl:element name="account_no"><xsl:value-of select="concat('F', account_no)"/></xsl:element>
        </xsl:when>
        <xsl:otherwise>
            <xsl:element name="account_no"><xsl:value-of select="account_no"/></xsl:element>
        </xsl:otherwise>
    </xsl:choose>
-->         
    <!-- base||supplementary ??? -->
    <xsl:choose>
                <xsl:when test='crd_main="PAMATKARTE" or crd_main="BASIC CARD"'>
                    <xsl:element name="base_sup">1</xsl:element>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:element name="base_sup">2</xsl:element>
                </xsl:otherwise>
    </xsl:choose>
    <!-- GROUPC --> 
    <xsl:element name="groupc">50</xsl:element>
    <!-- BIN --> 
       <!-- if card specified, will take first 6 chars, else choose... -->
    
     <xsl:if test="string-length(card) = 16">
        <xsl:element name="bin"><xsl:value-of select="substring(card,1,6)"/></xsl:element>
        <xsl:call-template name="set_chip_data">
            <xsl:with-param name="bin" select="substring(card,1,6)"/>
      <xsl:with-param name="auth" select="substring(card,7,1)"/>
        </xsl:call-template> 
     </xsl:if>
     
     <xsl:if test="$specialCard"><xsl:element name="auth_level">2</xsl:element></xsl:if>

    <!-- NEW CARD -->
     <xsl:if test="string-length(card) != 16">
            <xsl:choose>
                <xsl:when test='crd_type="VISA DEBIT"'>
                    <!-- ALGA AR LIGUMU goes to 465228, others to 477573 -->
                    <xsl:choose>
                        <xsl:when test='account_ccy = "USD"'>
                            <xsl:element name="bin">465228</xsl:element>
                            <xsl:element name="auth_level">2</xsl:element>
                            <xsl:call-template name="set_chip_data">
                                <xsl:with-param name="bin" select="465228"/>
                                <xsl:with-param name="auth" select="2"/>
                            </xsl:call-template> 
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:element name="bin">477573</xsl:element>
                            <xsl:element name="auth_level">3</xsl:element>
                            <xsl:call-template name="set_chip_data">
                                <xsl:with-param name="bin" select="477573"/>
                                <xsl:with-param name="auth" select="3"/>
                            </xsl:call-template>                                                   
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:when>

                <xsl:when test='crd_type="VISA CLASSIC"'>
                    <xsl:element name="bin">492175</xsl:element>
                    <xsl:element name="auth_level">4</xsl:element>
                    <xsl:call-template name="set_chip_data">
                        <xsl:with-param name="bin" select="492175"/>
                        <xsl:with-param name="auth" select="4"/>
                    </xsl:call-template>
                </xsl:when>
                
                <xsl:when test='crd_type="VISA CLASSIC REVOLVING"'>
                    <xsl:element name="bin">492175</xsl:element>
                    <xsl:element name="auth_level">0</xsl:element>
                    <xsl:call-template name="set_chip_data">
                        <xsl:with-param name="bin" select="492175"/>
                        <xsl:with-param name="auth" select="0"/>
                    </xsl:call-template>
                </xsl:when>
                
                <xsl:when test='crd_type="GALACTICO VISA DEBIT"'>
                    <xsl:element name="bin">477573</xsl:element>
                    <xsl:element name="auth_level">5</xsl:element>
                    <xsl:choose>
                        <xsl:when test='crd_sort="PRIVĀTĀ" or crd_sort="PRIVATE"'>
                            <xsl:element name="client_category">001</xsl:element>
                        </xsl:when>
                    </xsl:choose>
                    <xsl:call-template name="set_chip_data">
                                <xsl:with-param name="bin" select="477573"/>
                    <xsl:with-param name="auth" select="5"/>
                            </xsl:call-template>
                </xsl:when>

                <xsl:when test='crd_type="GALACTICO VISA CLASSIC"'>
                    <xsl:element name="bin">492175</xsl:element>
                    <xsl:element name="auth_level">5</xsl:element>
                    <xsl:choose>
                        <xsl:when test='crd_sort="PRIVĀTĀ" or crd_sort="PRIVATE"'>
                            <xsl:element name="client_category">001</xsl:element>
                        </xsl:when>
                    </xsl:choose>
                    <xsl:call-template name="set_chip_data">
                                <xsl:with-param name="bin" select="492175"/>
                    <xsl:with-param name="auth" select="5"/>
                            </xsl:call-template>
                </xsl:when>
                
                <xsl:when test='crd_type="VISA BUSINESS" and (crd_sort="JURIDISKĀ" or crd_sort="LEGAL")'>
                    <xsl:element name="bin">492176</xsl:element>
                    <xsl:call-template name="set_chip_data">
                                <xsl:with-param name="bin" select="492176"/>
                      <xsl:with-param name="auth" select="0"/>
                            </xsl:call-template>
                </xsl:when>

                <xsl:when test='crd_type="VISA BUSINESS DEBIT"'>
                    <xsl:element name="bin">442752</xsl:element>
                    <xsl:element name="auth_level">0</xsl:element>
                    <xsl:call-template name="set_chip_data">
                        <xsl:with-param name="bin" select="442752"/>
                        <xsl:with-param name="auth" select="0"/>
                    </xsl:call-template>
                </xsl:when>

                <xsl:when test='crd_type="VISA PLATINUM"'>
                    <xsl:element name="bin">499962</xsl:element>
                    <xsl:element name="auth_level">8</xsl:element>
                    <xsl:call-template name="set_chip_data">
                        <xsl:with-param name="bin" select="499962"/>
                        <xsl:with-param name="auth" select="8"/>
                    </xsl:call-template>
                </xsl:when>
                
                <xsl:when test='crd_type="VISA GOLD"'>
                    <xsl:element name="bin">499962</xsl:element>
                    <xsl:element name="auth_level">9</xsl:element>
                    <xsl:call-template name="set_chip_data">
                        <xsl:with-param name="bin" select="499962"/>
                        <xsl:with-param name="auth" select="9"/>
                    </xsl:call-template>
                </xsl:when>
                
                <xsl:when test='crd_type="VISA GOLD REVOLVING"'>
                    <xsl:element name="bin">499962</xsl:element>
                    <xsl:element name="auth_level">0</xsl:element>
                    <xsl:call-template name="set_chip_data">
                        <xsl:with-param name="bin" select="499962"/>
                        <xsl:with-param name="auth" select="0"/>
                    </xsl:call-template>
                </xsl:when>

                <xsl:when test='crd_type="LUMINOR VISA INFINITE"'>
                    <xsl:element name="auth_level">0</xsl:element>
                    <xsl:element name="bin">489994</xsl:element>
                    <xsl:call-template name="set_chip_data">
                        <xsl:with-param name="bin" select="489994"/>
                        <xsl:with-param name="auth" select="0"/>
                    </xsl:call-template>
                </xsl:when>

                <xsl:when test='crd_type="LUMINOR BLACK"'>
                    <xsl:element name="bin">478585</xsl:element>
                    <xsl:element name="auth_level">0</xsl:element>
                    <xsl:call-template name="set_chip_data">
                        <xsl:with-param name="bin" select="478585"/>
                        <xsl:with-param name="auth" select="0"/>
                    </xsl:call-template>
                </xsl:when>                

                <xsl:when test='crd_type="MAESTRO"'>
                    <xsl:element name="bin">676192</xsl:element>
                    <xsl:call-template name="set_chip_data">
                        <xsl:with-param name="bin" select="676192"/>
                        <xsl:with-param name="auth" select="0"/>
                    </xsl:call-template>
                </xsl:when>

                <xsl:when test='crd_type="MASTERCARD GOLD"'>
                    <xsl:element name="bin">542090</xsl:element>
                    <xsl:call-template name="set_chip_data">
                        <xsl:with-param name="bin" select="542090"/>
                        <xsl:with-param name="auth" select="0"/>
                    </xsl:call-template>
                </xsl:when>

                <xsl:when test='crd_type="MASTERCARD BUSINESS"'>
                    <xsl:element name="bin">547626</xsl:element>
                    <xsl:call-template name="set_chip_data">
                        <xsl:with-param name="bin" select="547626"/>
                        <xsl:with-param name="auth" select="0"/>
                    </xsl:call-template>
                </xsl:when>

                <xsl:when test='crd_type="MASTERCARD STANDARD"'>
                    <xsl:element name="bin">542085</xsl:element>
                    <xsl:call-template name="set_chip_data">
                        <xsl:with-param name="bin" select="542085"/>
                        <xsl:with-param name="auth" select="0"/>
                    </xsl:call-template>
                </xsl:when>

                <xsl:when test='crd_type="EE VISA BUSINESS" and (crd_sort="JURIDISKĀ" or crd_sort="LEGAL")'>
                    <xsl:element name="bin">408882</xsl:element>
                    <xsl:element name="auth_level">0</xsl:element>
                    <xsl:call-template name="set_chip_data">
                        <xsl:with-param name="bin" select="408882"/>
                        <xsl:with-param name="auth" select="0"/>
                    </xsl:call-template>
                    <xsl:if test="not(country)">
                        <xsl:element name="country">EE</xsl:element>
                    </xsl:if>
                </xsl:when>

                <xsl:when test='crd_type="EE LUMINOR VISA INFINITE"'>
                    <xsl:element name="bin">408883</xsl:element>
                    <xsl:element name="auth_level">0</xsl:element>
                    <xsl:call-template name="set_chip_data">
                        <xsl:with-param name="bin" select="408883"/>
                        <xsl:with-param name="auth" select="0"/>
                    </xsl:call-template>
                    <xsl:if test="not(country)">
                        <xsl:element name="country">EE</xsl:element>
                    </xsl:if>
                </xsl:when>

                <xsl:when test='crd_type="EE LUMINOR BLACK"'>
                    <xsl:element name="bin">408884</xsl:element>
                    <xsl:element name="auth_level">0</xsl:element>
                    <xsl:call-template name="set_chip_data">
                        <xsl:with-param name="bin" select="408884"/>
                        <xsl:with-param name="auth" select="0"/>
                    </xsl:call-template>
                    <xsl:if test="not(country)">
                        <xsl:element name="country">EE</xsl:element>
                    </xsl:if>
                </xsl:when>

                <xsl:when test='crd_type="EE VISA DEBIT"'>
                    <xsl:element name="bin">408885</xsl:element>
                    <xsl:element name="auth_level">0</xsl:element>
                    <xsl:call-template name="set_chip_data">
                        <xsl:with-param name="bin" select="408885"/>
                        <xsl:with-param name="auth" select="0"/>
                    </xsl:call-template>
                    <xsl:if test="not(country)">
                        <xsl:element name="country">EE</xsl:element>
                    </xsl:if>
                </xsl:when>

                <xsl:when test='crd_type="EE VISA BUSINESS DEBIT" and (crd_sort="JURIDISKĀ" or crd_sort="LEGAL")'>
                    <xsl:element name="bin">456946</xsl:element>
                    <xsl:element name="auth_level">0</xsl:element>
                    <xsl:call-template name="set_chip_data">
                        <xsl:with-param name="bin" select="456946"/>
                        <xsl:with-param name="auth" select="0"/>
                    </xsl:call-template>
                    <xsl:if test="not(country)">
                        <xsl:element name="country">EE</xsl:element>
                    </xsl:if>
                </xsl:when>

                <xsl:when test='crd_type="EE VISA CLASSIC"'>
                    <xsl:element name="bin">478590</xsl:element>
                    <xsl:element name="auth_level">0</xsl:element>
                    <xsl:call-template name="set_chip_data">
                        <xsl:with-param name="bin" select="478590"/>
                        <xsl:with-param name="auth" select="0"/>
                    </xsl:call-template>
                    <xsl:if test="not(country)">
                        <xsl:element name="country">EE</xsl:element>
                    </xsl:if>
                </xsl:when>

                <xsl:when test='crd_type="EE VISA GOLD"'>
                    <xsl:element name="bin">478591</xsl:element>
                    <xsl:element name="auth_level">0</xsl:element>
                    <xsl:call-template name="set_chip_data">
                        <xsl:with-param name="bin" select="478591"/>
                        <xsl:with-param name="auth" select="0"/>
                    </xsl:call-template>
                    <xsl:if test="not(country)">
                        <xsl:element name="country">EE</xsl:element>
                    </xsl:if>
                </xsl:when>
            </xsl:choose>
          </xsl:if>
          
          
<!-- now separate name and surname -->
       <!-- client name -->
            <xsl:element name="cl_lastname">
                <xsl:value-of select="substring-before(cl_name,' ')"/>
            </xsl:element>
            <xsl:element name="cl_firstname">
                <xsl:value-of select="substring-after(cl_name,' ')"/>
            </xsl:element>
       <!-- card owner name -->
            <xsl:choose>
                <xsl:when test='crd_sort="JURIDISKĀ" or crd_sort="LEGAL"'>
                    <xsl:element name="own_firstname">
                        <xsl:value-of select="substring-after(cl_name,' ')"/>
                    </xsl:element>
                    <xsl:element name="own_lastname">
                        <xsl:value-of select="substring-before(cl_name,' ')"/>
                    </xsl:element>              
                </xsl:when>
                <xsl:otherwise>
                    <xsl:element name="own_firstname">
                        <xsl:value-of select="translate(substring-after(own_name,' '),'&quot;','')"/>
                    </xsl:element>
                    <xsl:element name="own_lastname">
                        <xsl:value-of select="translate(substring-before(own_name,' '),'&quot;','')"/>
                    </xsl:element>                              
                </xsl:otherwise>
            </xsl:choose>
            
            <xsl:if test='crd_sort="JURIDISKĀ" or crd_sort="LEGAL"'>
                <xsl:element name="own_company_name">
                <xsl:value-of select="substring(translate(own_name,'&quot;',''),1,24)"/>
                </xsl:element>
            </xsl:if>
    <!-- We probably want separate address into three lines ... here we go ! -->

    <!-- Account owner address -->

            <xsl:element name="own_addr_street">
                <xsl:value-of select="substring(normalize-space(concat(own_addr_street1,' ',own_addr_street2)),1,190)"/>
            </xsl:element>
            <xsl:for-each select="own_addr_city|own_addr_zip">
                <xsl:copy-of select="."/>
            </xsl:for-each>
            <xsl:element name="own_addr_country">
                <xsl:value-of select="translate(own_addr_country,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/>
            </xsl:element>
            
    <!-- Statement address -->
            <xsl:choose>
                <xsl:when test="count(crd_addr_street1)=1">
                    <xsl:element name="crd_addr_street">
                        <xsl:value-of select="normalize-space(concat(crd_addr_street1,' ',crd_addr_street2))"/>
                    </xsl:element>
                    <xsl:for-each select="crd_addr_city|crd_addr_zip">
                        <xsl:copy-of select="."/>
                    </xsl:for-each>
                    <xsl:element name="crd_addr_country">
                        <xsl:value-of select="translate(crd_addr_country,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/>
                    </xsl:element>
                    
                </xsl:when>
                
                <xsl:otherwise>
                    <xsl:element name="crd_addr_street">
                        <xsl:value-of select="normalize-space(concat(own_addr_street1,' ',own_addr_street2))"/>
                    </xsl:element>
                    <xsl:element name="crd_addr_city">
                        <xsl:value-of select="own_addr_city"/>
                    </xsl:element>
                    <xsl:element name="crd_addr_zip">
                        <xsl:value-of select="own_addr_zip"/>
                    </xsl:element>
                    <xsl:element name="crd_addr_country">
                        <xsl:value-of select="translate(own_addr_country,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/>
                    </xsl:element>
                </xsl:otherwise>
            </xsl:choose>

            
    <!-- Resident/not resident -->
            <xsl:choose>
                <xsl:when test='starts-with(RESIDENT,"R")'>
                    <xsl:element name="resident">1</xsl:element>
                </xsl:when>
                <xsl:when test='starts-with(RESIDENT,"N")'>
                    <xsl:element name="resident">2</xsl:element>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:element name="resident">0</xsl:element>
                </xsl:otherwise>
            </xsl:choose>

    <!-- card validity period -->


            <xsl:element name="crd_period">
                <xsl:value-of select="substring-before(crd_period,' ')"/>
            </xsl:element>

            <xsl:element name="crd_expiry">
                <xsl:choose>
                    <xsl:when test="string-length(substring(fill_date,7,2)+substring-before(crd_period,' ')) = 1">
                         <xsl:value-of select="concat(
                            substring(fill_date,4,2),
                            '0',substring(fill_date,7,2)+substring-before(crd_period,' '))"/>
                    </xsl:when>
                    <xsl:when test="string-length((substring(fill_date,7,2)+substring-before(crd_period,' '))) = 2">
                         <xsl:value-of select="concat(
                            substring(fill_date,4,2),
                            substring(fill_date,7,2)+substring-before(crd_period,' '))"/>
                    </xsl:when>
                    <xsl:when test="string-length((substring(fill_date,7,2)+substring-before(crd_period,' '))) = 3">
                         <xsl:value-of select="concat(
                            substring(fill_date,4,2),
                            substring(substring(fill_date,7,2)+substring-before(crd_period,' '),1,2))"/>
                    </xsl:when>

                </xsl:choose>
            </xsl:element>
            
            

<!-- now based on crd_sort we should set client_type and category -->
            <xsl:choose>
                <xsl:when test='crd_sort="PRIVĀTĀ" or crd_sort="PRIVATE"'>
                    <xsl:element name="client_type">1</xsl:element>
                </xsl:when>
                <xsl:when test='crd_sort="ALGAS AR LĪGUMU" or crd_sort="SALARY WITH CONTRACT"'>
                    <xsl:element name="client_type">1</xsl:element>
                    <xsl:element name="client_category">100</xsl:element>
                    <xsl:element name="u_cod9">001</xsl:element>
                        <!-- Employment -->
                    <xsl:element name="emp_company"><xsl:value-of select="REGN_COMP"/></xsl:element>                
                </xsl:when>
                <xsl:when test='crd_sort="ALGAS AR IZZIŅU" or crd_sort="SALARY WITH CERTIFICATE"'>
                    <xsl:element name="client_type">1</xsl:element>
                    <xsl:element name="client_category">101</xsl:element>
                    <xsl:element name="u_cod9">002</xsl:element>
                    <!-- It is decided not to give this to CMS, just to boost automated flow -->
                    <!--xsl:element name="emp_company"><xsl:value-of select="REGN_COMP"/></xsl:element-->               
                </xsl:when>
                <xsl:when test='crd_sort="PENSIJAS/PABALSTA" or crd_sort="PENSION/ALLOWANCE"'>
                    <xsl:element name="client_type">1</xsl:element>
                    <xsl:element name="client_category">102</xsl:element>
                </xsl:when>
                <xsl:when test='crd_sort="STUDENTA" or crd_sort="STUDENT"'>
                    <xsl:element name="client_type">1</xsl:element>
                    <xsl:element name="client_category">103</xsl:element>
                </xsl:when>
                <xsl:when test='crd_sort="JURIDISKĀ" or crd_sort="LEGAL"'>
                    <xsl:choose>
                        <xsl:when test='crd_type = "VISA DEBIT" or crd_type = "MAESTRO" or crd_type="VISA BUSINESS" or crd_type="VISA BUSINESS DEBIT" or crd_type="MASTERCARD BUSINESS" or crd_type="EE VISA BUSINESS DEBIT"'>
                            <xsl:element name="card_type">03</xsl:element>
                        </xsl:when>
                        <xsl:when test='crd_type = "EE VISA BUSINESS"'>
                            <xsl:element name="card_type">02</xsl:element>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:element name="emp_company"><xsl:value-of select="REGN_COMP"/></xsl:element>                
                        </xsl:otherwise>
                    </xsl:choose>
                    <xsl:element name="client_type">2</xsl:element>
                </xsl:when>
                <xsl:when test='crd_sort="BĒRNA" or crd_sort="CHILDREN"'>
                    <xsl:element name="crd_sort">CHILDREN</xsl:element>
                    <xsl:element name="client_type">1</xsl:element>
                    <xsl:element name="u_cod9">006</xsl:element>
                    <xsl:element name="client_category">006</xsl:element>
                </xsl:when>
                <xsl:when test='crd_sort="PUSAUDŽA" or crd_sort="TEEN"'>
                    <xsl:element name="crd_sort">TEEN</xsl:element>
                    <xsl:element name="client_type">1</xsl:element>
                    <xsl:element name="u_cod9">007</xsl:element>
                    <xsl:element name="client_category">007</xsl:element>
                </xsl:when>
            </xsl:choose>

        <!-- now we need transfer ugly constants to Transmaster languages<crd_stmt_lang>LATVIEŠU</crd_stmt_lang>
        -->         
            <xsl:element name="rep_lang">
            <xsl:choose>
                <xsl:when test='crd_stmt_lang="LV" or crd_stmt_lang="LATVIEŠU" or crd_stmt_lang="Latvian" or crd_stmt_lang="LATVIAN" '>2</xsl:when>
                <xsl:when test='crd_stmt_lang="EN" or crd_stmt_lang="ANGĻU"    or crd_stmt_lang="English" or crd_stmt_lang="ENGLISH" '>1</xsl:when>
                <xsl:when test='crd_stmt_lang="RU" or crd_stmt_lang="KRIEVU"   or crd_stmt_lang="Russian" or crd_stmt_lang="RUSSIAN" '>3</xsl:when>
                <xsl:when test='crd_stmt_lang="EE" or crd_stmt_lang="ESTONIAN" or crd_stmt_lang="Estonian"'>8</xsl:when>
            </xsl:choose>
            </xsl:element>
            
            <xsl:element name="dlv_language">
            <xsl:choose>
                <xsl:when test='dlv_language="LV" or dlv_language="LATVIEŠU" or dlv_language="Latvian" or dlv_language="LATVIAN" '>2</xsl:when>
                <xsl:when test='dlv_language="EN" or dlv_language="ANGĻU"    or dlv_language="English" or dlv_language="ENGLISH" '>1</xsl:when>
                <xsl:when test='dlv_language="RU" or dlv_language="KRIEVU"   or dlv_language="Russian" or dlv_language="RUSSIAN" '>3</xsl:when>
                <xsl:when test='dlv_language="EE" or dlv_language="ESTONIAN" or dlv_language="Estonian"'>8</xsl:when>
                <xsl:otherwise><xsl:value-of select="dlv_language"/></xsl:otherwise>
            </xsl:choose>
            </xsl:element>

            <xsl:element name="rep_distribution_mode">
            <xsl:choose>
                <xsl:when test='crd_stmt="E-PASTS" or crd_stmt="E-MAIL"'>88</xsl:when>
                <xsl:when test='crd_stmt="PA PASTU" or crd_stmt="BY MAIL"'>11</xsl:when>
                <xsl:when test='crd_stmt="BANKĀ" or crd_stmt="Bank"'>00</xsl:when>
                <xsl:when test='crd_stmt="NEVĒLOS" or crd_stmt="NONE"'>99</xsl:when>
                <xsl:otherwise>99</xsl:otherwise>
            </xsl:choose>
            </xsl:element>

            <xsl:element name="iban">
                <xsl:value-of select="account_iban"/>
            </xsl:element>

            <xsl:element name="repeated">
                <xsl:value-of select="REPEATED"/>
            </xsl:element>

        </xsl:element>
    </xsl:for-each>
  </xsl:element>
</xsl:template>

</xsl:stylesheet>
