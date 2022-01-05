<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">
    <xsl:param name="ename">groupc</xsl:param>
    <xsl:param name="evalue">02</xsl:param>

    <xsl:output method="xml" encoding="utf-8"/>

    <xsl:template match="groupc">
        <xsl:element name="{$ename}">
            <xsl:value-of select="$evalue"/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="* | @*">
        <xsl:copy>
            <xsl:copy-of select="@*"/>
            <xsl:apply-templates/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="condition_set">
        <xsl:element name="card_condition_set">
            <xsl:value-of select="."/>
        </xsl:element>
        <xsl:element name="account_condition_set">
            <xsl:value-of select="."/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="crd_place">
        <xsl:element name="crd_place">
            <xsl:value-of select="."/>
        </xsl:element>
        <xsl:element name="branch">
            <xsl:value-of select="."/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="cl_lastname">
        <xsl:if test="../client_type = '2'">
            <xsl:element name="cl_lastname">
                <xsl:value-of select="substring(.,1,34)"/>
            </xsl:element>
        </xsl:if>
        <xsl:if test="../client_type = '1'">
            <xsl:element name="cl_lastname">
                <xsl:value-of select="."/>
            </xsl:element>
        </xsl:if>
    </xsl:template>

    <xsl:template match="card">
        <xsl:choose>
            <xsl:when
                    test="not((../@action='cardRenew' or ../application_type='AutoRenewal')  and ((substring(../card,1,6)='431402') or substring(../card,1,6)='676399' or substring(../card,1,6)='546762' or substring(../card,1,6)='546763' or substring(../card,1,6)='547808' or substring(../card,1,7)='4314008' or substring(../card,1,6)='406105'))">
                <xsl:element name="card">
                    <xsl:value-of select="."/>
                </xsl:element>
                <xsl:if test="not(../bin)">
                    <xsl:call-template name="set_chip_data">
                        <xsl:with-param name="bin" select="substring(../card,1,6)"/>
                    </xsl:call-template>
                </xsl:if>
            </xsl:when>
            <xsl:otherwise>
                <xsl:element name="old_card">
                    <xsl:value-of select="."/>
                </xsl:element>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>


    <xsl:template match="bin">
        <xsl:if test="not((../@action='cardRenew' or ../application_type='AutoRenewal')  and ((substring(../card,1,6)='431402') or substring(../card,1,6)='676399' or substring(../card,1,6)='546762' or substring(../card,1,6)='546763' or substring(../card,1,6)='547808' or substring(../card,1,7)='4314008' or substring(../card,1,6)='406105'))">
            <xsl:call-template name="set_chip_data">
                <xsl:with-param name="bin" select="."/>
            </xsl:call-template>
        </xsl:if>
    </xsl:template>

    <xsl:template match="branch">
        <xsl:if test="not(../crd_place)">
            <!-- <xsl:element name="branch"><xsl:value-of select="."/></xsl:element> -->
        </xsl:if>
    </xsl:template>

    <xsl:template match="auto_renew_flag">
        <xsl:if test=". = '0'">
            <xsl:element name="auto_renew_flag">N</xsl:element>
        </xsl:if>
        <xsl:if test=". = '1'">
            <xsl:element name="auto_renew_flag">J</xsl:element>
        </xsl:if>
    </xsl:template>

    <xsl:template name="set_chip_data">
        <xsl:param name="bin"/>
        <xsl:element name="bin">
            <xsl:value-of select="$bin"/>
        </xsl:element>
        <xsl:element name="off_cond_set">0</xsl:element>
        <xsl:element name="application_id">
            <xsl:choose>
                <xsl:when test="$bin = '422074'">216</xsl:when>
                <xsl:when test="$bin = '431400'">216</xsl:when>
                <xsl:when test="$bin = '422073'">216</xsl:when>
                <xsl:when test="$bin = '406105'">216</xsl:when>
                <xsl:when test="$bin = '431402'">216</xsl:when>
                <xsl:when test="$bin = '431401'">216</xsl:when>
                <xsl:when test="$bin = '431419'">216</xsl:when>
                <xsl:when test="$bin = '547808'">202</xsl:when>
                <xsl:when test="$bin = '546762'">202</xsl:when>
                <xsl:when test="$bin = '546763'">202</xsl:when>
                <xsl:when test="$bin = '676399'">203</xsl:when>
                <xsl:when test="$bin = '469139'">216</xsl:when>
                <xsl:when test="$bin = '478586'">216</xsl:when>
            </xsl:choose>
        </xsl:element>
        <xsl:element name="chip_design_id">
            <xsl:choose>
                <xsl:when test="$bin = '422074'">2</xsl:when>
                <xsl:when test="$bin = '547808'">3</xsl:when>
                <xsl:when test="$bin = '431400'">1</xsl:when>
                <xsl:when test="$bin = '422073'">1</xsl:when>
                <xsl:when test="$bin = '676399'">4</xsl:when>
                <xsl:when test="$bin = '546763'">3</xsl:when>
                <xsl:when test="$bin = '406105'">2</xsl:when>
                <xsl:when test="$bin = '431402'">2</xsl:when>
                <xsl:when test="$bin = '546762'">3</xsl:when>
                <xsl:when test="$bin = '431401'">1</xsl:when>
                <xsl:when test="$bin = '431419'">1</xsl:when>
                <xsl:when test="$bin = '469139'">1</xsl:when>
                <xsl:when test="$bin = '478586'">1</xsl:when>
            </xsl:choose>
        </xsl:element>
        <xsl:element name="rep_distribution_mode">03</xsl:element>
    </xsl:template>

    <xsl:template match="emp_company">
        <xsl:if test=". = ''">
            <xsl:element name="emp_company">000000</xsl:element>
        </xsl:if>
        <xsl:if test=". != ''">
            <xsl:element name="emp_company">
                <xsl:value-of select="."/>
            </xsl:element>
        </xsl:if>
    </xsl:template>

    <xsl:template match="order">
        <xsl:element name="order">
            <xsl:variable name="specialCard"
                          select="(@action='cardRenew' or application_type='AutoRenewal')  and (substring(card,1,6)='431402' or substring(card,1,6)='676399' or substring(card,1,6)='546762' or substring(card,1,6)='546763' or substring(card,1,6)='547808' or substring(card,1,7)='4314008' or substring(card,1,6)='406105')"/>
            <xsl:attribute name="action">
                <xsl:choose>
                    <xsl:when test='$specialCard'>cardCreate</xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="@action"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:attribute>
            <xsl:apply-templates/>
            <xsl:if test="@action[.='cardReplace']">
                <xsl:element name="{$ename}">
                    <xsl:value-of select="$evalue"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="$specialCard">
                <xsl:variable name="newBin">
                    <xsl:choose>
                        <!-- new cards -->
                        <xsl:when test="substring(card,1,6)='431402'">422074</xsl:when>
                        <xsl:when test='substring(card,1,6)="676399"'>422074</xsl:when>
                        <xsl:when test='substring(card,1,6)="547808"'>431401</xsl:when>
                        <xsl:when test='substring(card,1,6)="546763"'>431419</xsl:when>
                        <xsl:when test='substring(card,1,6)="546762"'>431400</xsl:when>
                        <xsl:when test='substring(card,1,6)="406105"'>422073</xsl:when>
                        <xsl:when test='substring(card,1,7)="4314008"'>431400</xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="substring(card,1,6)"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>
                <xsl:call-template name="set_chip_data">
                    <xsl:with-param name="bin" select="$newBin"/>
                </xsl:call-template>
            </xsl:if>

        </xsl:element>
    </xsl:template>

    <xsl:template match="IBAN">
        <xsl:element name="iban">
            <xsl:copy-of select="@*"/>
            <xsl:apply-templates/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="auth_limit">
        <xsl:element name="auth_level">
            <xsl:copy-of select="@*"/>
            <xsl:apply-templates/>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>
