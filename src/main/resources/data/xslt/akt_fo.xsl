<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:a="http://www.skustinans.rs/akti"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"

                version="2.0">


    <xsl:variable name="status" select="/*/a:preabula/a:pravniOsnov"/>

    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="akt-stranica">
                    <fo:region-body margin="0.75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <!-- AKT -->
            <fo:page-sequence master-reference="akt-stranica">
                <fo:flow flow-name="xsl-region-body">

                    <!-- NASLOV AKTA -->
                    <fo:block font-family="Arial" text-align="center" font-size="22px" font-weight="700" margin="10px">
                        <xsl:value-of select="/*/@naziv"/>
                    </fo:block>

                    <!-- CLANOVI -->
                    <xsl:apply-templates select="child::*/child::a:clan"/>

                    <!-- DELOVI  -->
                    <xsl:for-each select="child::*/child::a:deo">

                        <!-- NASLOV DELA -->
                        <fo:block id="{@id}" font-family="Arial" text-align="center" font-size="20px" font-weight="bold" margin-top="50px">
                            <xsl:value-of select="@redniBroj"/>
                        </fo:block>
                        <xsl:text>&#xa;</xsl:text>
                        <fo:block font-family="Arial" text-align="center" font-size="18px" font-weight="bold" margin-top="15px">
                            <xsl:value-of select="@naslov"/>
                        </fo:block>

                        <!-- CLANOVI -->
                        <xsl:apply-templates select="a:clan"/>

                        <!-- GLAVE -->
                        <xsl:for-each select="a:glava">
                            <fo:block id="{@id}" font-family="Arial" text-align="center" font-size="18px" margin-top="40px" margin-bottom="10px">
                                <xsl:value-of select="@redniBroj"/>
                                <xsl:text> </xsl:text>
                                <xsl:value-of select="@naslov"/>
                            </fo:block>

                            <xsl:apply-templates select="a:clan"/>

                            <!-- ODELJCI -->
                            <xsl:for-each select="a:odeljak">
                                <fo:block id="{@id}" font-family="Arial" text-align="center" font-size="16px" margin-bottom="10px" margin-top="35px">
                                    <xsl:value-of select="@redniBroj"/>
                                    <xsl:text> </xsl:text>
                                    <xsl:value-of select="@naslov"/>
                                </fo:block>

                                <xsl:apply-templates select="akt:Clan"/>

                                <!-- PODODELJCI -->
                                <xsl:for-each select="akt:Pododeljak">
                                    <fo:block id="{@id}" font-family="Arial" text-align="center" font-size="14px" margin-bottom="20px" margin-top="25px">
                                        <xsl:value-of select="@redniBroj"/>
                                        <xsl:text> </xsl:text>
                                        <xsl:value-of select="@naslov"/>
                                    </fo:block>
                                    <xsl:apply-templates select="akt:Clan"/>
                                </xsl:for-each>

                            </xsl:for-each>

                        </xsl:for-each>

                    </xsl:for-each>

                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>

    <xsl:template match="a:clan">

        <fo:block id="{@id}" font-family="Arial" text-align="center" font-size="13px" margin-top="20px">
            <fo:inline font-weight="bold">
                <xsl:value-of select="@redniBroj"/>
            </fo:inline>
        </fo:block>
        <xsl:text>&#xa;</xsl:text>
        <fo:block font-family="Arial" text-align="center" font-size="13px" font-weight="bold" margin-top="10px">
            <xsl:value-of select="@naslov"/>
        </fo:block>

        <xsl:for-each select="akt:Stav">
            <fo:block id="{@id}" font-family="Arial" font-size="12" text-align="justify" text-indent="40px" margin="10px">
                <!-- <xsl:apply-templates select="akt:Sadrzaj"/> -->
                <xsl:apply-templates select="akt:Tacka"/>
            </fo:block>
        </xsl:for-each>
    </xsl:template>


    <!-- <xsl:template match="akt:Tekst">
    		<xsl:apply-templates />
    </xsl:template>

    <xsl:template match="akt:Tekst//akt:Referenca">
    	<xsl:variable name="pre" select="substring-before($status,' ')"/>
        <xsl:variable name="posle" select="substring-after($status,' ')"/>
        <xsl:variable name="statusAkta" select="concat(lower-case(substring($pre,1)), upper-case(substring($posle,1,1)), substring($posle, 2))"/>
	    <xsl:choose>
	    	<xsl:when test="substring(@URL, 1, 1) != '#'">
	        	<fo:basic-link color="#618587" show-destination="new"
	        		external-destination="url(http://localhost:8080/#/akti/{$statusAkta}/{@URL})">
            		<xsl:value-of select="text()"/>
            	</fo:basic-link>
	        </xsl:when>
			 kada referenca pocinje sa #, referencira se unutar dokumenta
	        <xsl:otherwise>
	        	<xsl:variable name="referenca" select="substring-after(@URL,'#')"/>
	        	<fo:basic-link color="#876185" internal-destination="{$referenca}">
						<xsl:value-of select="text()"/>	</fo:basic-link>
	        </xsl:otherwise>
		</xsl:choose>
    </xsl:template> -->

    <xsl:template match="a:tacka">
        <fo:block id="{@id}" font-family="Arial" font-size="12" text-align="justify" text-indent="40px" margin-bottom="10px" margin-top="10px">
            <xsl:value-of select="@br"/>
            <xsl:text> </xsl:text>
            <xsl:value-of select="akt:Sadrzaj"/>
            <xsl:apply-templates select="akt:Podtacka"/>
        </fo:block>
    </xsl:template>

    <xsl:template match="a:podtacka">
        <fo:block id="{@id}" font-family="Arial" font-size="12" text-align="justify" text-indent="40px" margin-bottom="10px" margin-left="40px" margin-top="10px">
            <xsl:value-of select="@redniBroj"/>
            <xsl:text> </xsl:text>
            <xsl:value-of select="a:sadrzaj"/>
            <xsl:apply-templates select="a:alineja"/>
        </fo:block>
    </xsl:template>

    <xsl:template match="a:alineja">
        <fo:block id="{@id}" font-family="Arial" font-size="12" text-align="justify" margin-bottom="10px" margin-left="80px">
            <xsl:value-of select="a:sadrzaj"/>
        </fo:block>
    </xsl:template>

</xsl:stylesheet>
