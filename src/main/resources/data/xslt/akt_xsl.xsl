<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:a="http://www.skustinans.rs/akti"
                version="2.0">

    <xsl:output encoding="UTF-8" />
    <xsl:variable name="aktId" select="/*/@id" />
    <xsl:variable name="smallcase" select="'abcdefghijklmnopqrstuvwxyzšđž'" />
    <xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZŠĐŽ'" />

    <xsl:variable name="naslovAkta" select="/*/@naziv" />
    <xsl:variable name="preambula" select="/*/a:preambula" />


    <!-- AKT -->
    <xsl:template match="/">
        <h1 class="tim_wro">
            <xsl:value-of select="translate($preambula, $smallcase, $uppercase)" />
        </h1>
        <h1 class="tim_wro">
            <xsl:value-of select="translate($naslovAkta, $smallcase, $uppercase)" />
        </h1>

        <!-- CLANOVI -->
        <xsl:apply-templates select="child::*/child::a:clan" />

        <!-- DELOVI -->
        <xsl:for-each select="child::*/child::*/child::a:deo">
            <xsl:variable name="naslovDela" select="@naziv" />
            <div class="sakriveno" id="{@id}"></div>
            <h2 class="tim_wro naslovDeo">
                <xsl:value-of select="@br" />
            </h2>
            <br></br>
            <h3 class="tim9">
                <xsl:value-of select="translate($naslovDela, $smallcase, $uppercase)" />
            </h3>

      <!--      <xsl:apply-templates select="a:clan" /> -->

            <!-- GLAVE -->
            <xsl:for-each select="a:glava">
                <xsl:variable name="naslovGlave" select="@naziv" />
                <div class="sakriveno" id="{@id}"></div>
                <h3 class="tim_wro naslovGlava">
                    <xsl:value-of select="@br" />
                    <xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
                    <xsl:value-of select="translate($naslovGlave, $smallcase, $uppercase)" />
                </h3>

                <xsl:apply-templates select="a:clan" />

                <!-- ODELJCI -->
                <div class="sakriveno" id="{@id}"></div>
                <xsl:for-each select="a:odeljak">
                    <h4 class="tim_wro naslovOdeljak">
                        <xsl:value-of select="@br" />
                        <xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
                        <xsl:value-of select="@naziv" />
                    </h4>

                    <xsl:apply-templates select="a:clan" /> -->

                    <!-- PODODELJCI -->
                    <div class="sakriveno" id="{@id}"></div>
                    <xsl:for-each select="a:pododeljak">
                        <h5 class="tim_wro naslovPododeljak">
                            <xsl:value-of select="@br" />
                            <xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
                            <xsl:value-of select="@naziv" />
                        </h5>
                        <xsl:apply-templates select="a:clan" />
                    </xsl:for-each>


                </xsl:for-each>

            </xsl:for-each>

        </xsl:for-each>

    </xsl:template>

    <xsl:template match="a:clan">
        <div class="sakriveno" id="{@id}"></div>
        <h6 class="tim9 naslovClan"><xsl:value-of select="@naziv"/></h6>
        <h6 class="tim9"><xsl:value-of select="@br"/></h6>
        <xsl:for-each select="a:stav">
            <div class="sakriveno" id="{@id}"></div>
            <xsl:apply-templates select="a:sadrzaj"/>
            <xsl:apply-templates select="a:tacka"/>
        </xsl:for-each>
    </xsl:template>

    <xsl:template match="a:sadrzaj">
        <p class="tim9 uvucen">
            <xsl:apply-templates />
        </p>
    </xsl:template>

    <xsl:template match="a:tacka">
        <p class="tim9 uvucen tacka">
            <div class="sakriveno" id="{@id}"></div>
            <xsl:value-of select="@br"/>
            <xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
            <xsl:value-of select="a:sadrzaj"/>
            <xsl:apply-templates select="a:podtacka"/>
        </p>
    </xsl:template>

    <xsl:template match="a:podtacka">
        <p class="tim9 uvucen podtacka">
            <div class="sakriveno" id="{@id}"></div>
            <xsl:value-of select="@br"/>
            <xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
            <xsl:value-of select="a:sadrzaj"/>
            <xsl:apply-templates select="a:alineja"/>
        </p>
    </xsl:template>

    <xsl:template match="a:alineja">
        <p class="tim9 uvucen alineja">
            <div class="sakriveno" id="{@id}"></div>
            <xsl:value-of select="a:sadrzaj"/>
        </p>
    </xsl:template>

</xsl:stylesheet>