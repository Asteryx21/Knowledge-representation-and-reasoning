<?xml version="1.0"?>
	<xsl:stylesheet  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
	  <html>
	  <body>
		<h2>Schedule</h2>
		<table border="1">
		  <tr bgcolor="#999999">
			<th>Title</th>
			<th>Professor</th>
			<th>Day</th>
		  </tr>
		  <xsl:for-each select="/Schedule/Lesson/Lecture[Day='Monday']">
			<tr>
				 <td bgcolor="#FF5733"><xsl:value-of select="../Title"/></td>
				 <td bgcolor="#FF5733"><xsl:value-of select="../Professor"/></td>
				 <td bgcolor="#FF5733"><xsl:value-of select="Day"/></td>
			</tr>
		  </xsl:for-each>
		  <xsl:for-each select="/Schedule/Lesson/Lecture[Day='Tuesday']">
			<tr>
				 <td bgcolor="#F38F00"><xsl:value-of select="../Title"/></td>
				 <td bgcolor="#F38F00"><xsl:value-of select="../Professor"/></td>
				 <td bgcolor="#F38F00"><xsl:value-of select="Day"/></td>
			</tr>
		  </xsl:for-each>
		  <xsl:for-each select="/Schedule/Lesson/Lecture[Day='Wednesday']">
			<tr>
				 <td bgcolor="#33C0FF"><xsl:value-of select="../Title"/></td>
				 <td bgcolor="#33C0FF"><xsl:value-of select="../Professor"/></td>
				 <td bgcolor="#33C0FF"><xsl:value-of select="Day"/></td>
			</tr>
		  </xsl:for-each>
		  <xsl:for-each select="/Schedule/Lesson/Lecture[Day='Thursday']">
			<tr>
				 <td bgcolor="#E7FF33"><xsl:value-of select="../Title"/></td>
				 <td bgcolor="#E7FF33"><xsl:value-of select="../Professor"/></td>
				 <td bgcolor="#E7FF33"><xsl:value-of select="Day"/></td>
			</tr>
		  </xsl:for-each>
		  <xsl:for-each select="/Schedule/Lesson/Lecture[Day='Friday']">
			<tr>
				 <td bgcolor="#E733FF"><xsl:value-of select="../Title"/></td>
				 <td bgcolor="#E733FF"><xsl:value-of select="../Professor"/></td>
				 <td bgcolor="#E733FF"><xsl:value-of select="Day"/></td>
			</tr>
		  </xsl:for-each>

		</table>
	  </body>
	  </html>
	</xsl:template>

	</xsl:stylesheet>