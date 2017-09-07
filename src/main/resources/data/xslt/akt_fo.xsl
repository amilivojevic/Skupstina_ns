<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:a="http://www.skustinans.rs/akti"
    xmlns:fo="http://www.w3.org/1999/XSL/Format" 
    version="2.0">
    
  <!--  <xsl:variable name="status" select="/*/akt:preambula/akt:Status"/> -->
    
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
                        This block of output will have a one millimeter border around it.
                    </fo:block>

               	    
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
    

</xsl:stylesheet>
