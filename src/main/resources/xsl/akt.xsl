<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                xmlns:a="http://www.skustinans.rs/akti"
                xmlns:svojstvo="http://www.skustinans.rs/akti/">

    <xsl:template match="/a:akt">
        <rdf:Description rdf:about="http://www.skustinans.rs/akti/{@id}">
            <svojstvo:naziv><xsl:value-of select="@naziv"/></svojstvo:naziv>
            <svojstvo:drzava><xsl:value-of select="@drzava"/></svojstvo:drzava>
            <svojstvo:regija><xsl:value-of select="@regija"/></svojstvo:regija>
            <svojstvo:grad><xsl:value-of select="@grad"/></svojstvo:grad>
            <svojstvo:kreirao><xsl:value-of select="@kreirao"/></svojstvo:kreirao>
        </rdf:Description>
    </xsl:template>

</xsl:stylesheet>