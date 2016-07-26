package edu.ucdenver.ccp.datasource.fileparsers.ebi.goa.gaf;

/*
 * #%L
 * Colorado Computational Pharmacology's common module
 * %%
 * Copyright (C) 2012 - 2015 Regents of the University of Colorado
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the Regents of the University of Colorado nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

import org.apache.log4j.Logger;

import edu.ucdenver.ccp.common.string.StringUtil;
import edu.ucdenver.ccp.datasource.fileparsers.ebi.goa.GoRefID;
import edu.ucdenver.ccp.datasource.identifiers.DataSourceIdentifier;
import edu.ucdenver.ccp.datasource.identifiers.IdResolver;
import edu.ucdenver.ccp.datasource.identifiers.ProteinAccessionResolver;
import edu.ucdenver.ccp.datasource.identifiers.UnknownDataSourceIdentifier;
import edu.ucdenver.ccp.datasource.identifiers.ebi.embl.EmblID;
import edu.ucdenver.ccp.datasource.identifiers.ebi.intact.IntActID;
import edu.ucdenver.ccp.datasource.identifiers.ebi.interpro.InterProID;
import edu.ucdenver.ccp.datasource.identifiers.ebi.interpro.PantherID;
import edu.ucdenver.ccp.datasource.identifiers.ebi.uniprot.UniProtID;
import edu.ucdenver.ccp.datasource.identifiers.ebi.uniprot.UniProtIsoformID;
import edu.ucdenver.ccp.datasource.identifiers.ec.EnzymeCommissionID;
import edu.ucdenver.ccp.datasource.identifiers.ensembl.EnsemblGeneID;
import edu.ucdenver.ccp.datasource.identifiers.flybase.FlyBaseID;
import edu.ucdenver.ccp.datasource.identifiers.hgnc.HgncID;
import edu.ucdenver.ccp.datasource.identifiers.mgi.MgiGeneID;
import edu.ucdenver.ccp.datasource.identifiers.ncbi.GenBankID;
import edu.ucdenver.ccp.datasource.identifiers.ncbi.gene.EntrezGeneID;
import edu.ucdenver.ccp.datasource.identifiers.ncbi.gene.GiNumberID;
import edu.ucdenver.ccp.datasource.identifiers.ncbi.omim.OmimID;
import edu.ucdenver.ccp.datasource.identifiers.ncbi.refseq.RefSeqID;
import edu.ucdenver.ccp.datasource.identifiers.obo.CellTypeOntologyID;
import edu.ucdenver.ccp.datasource.identifiers.obo.ChebiOntologyID;
import edu.ucdenver.ccp.datasource.identifiers.obo.GeneOntologyID;
import edu.ucdenver.ccp.datasource.identifiers.obo.MammalianPhenotypeID;
import edu.ucdenver.ccp.datasource.identifiers.obo.ProteinOntologyId;
import edu.ucdenver.ccp.datasource.identifiers.obo.SequenceOntologyId;
import edu.ucdenver.ccp.datasource.identifiers.obo.UberonID;
import edu.ucdenver.ccp.datasource.identifiers.other.EcoGeneID;
import edu.ucdenver.ccp.datasource.identifiers.other.HAMAPId;
import edu.ucdenver.ccp.datasource.identifiers.other.PomBaseId;
import edu.ucdenver.ccp.datasource.identifiers.other.PubChemCompoundId;
import edu.ucdenver.ccp.datasource.identifiers.other.PubChemSubstanceId;
import edu.ucdenver.ccp.datasource.identifiers.other.RnaCentralId;
import edu.ucdenver.ccp.datasource.identifiers.other.TairID;
import edu.ucdenver.ccp.datasource.identifiers.other.UniPathwayId;
import edu.ucdenver.ccp.datasource.identifiers.other.ZfinID;
import edu.ucdenver.ccp.datasource.identifiers.reactome.ReactomeReactionID;
import edu.ucdenver.ccp.datasource.identifiers.rgd.NboId;
import edu.ucdenver.ccp.datasource.identifiers.rgd.PwId;
import edu.ucdenver.ccp.datasource.identifiers.rgd.RdoId;
import edu.ucdenver.ccp.datasource.identifiers.rgd.RgdID;
import edu.ucdenver.ccp.datasource.identifiers.sgd.SgdID;
import edu.ucdenver.ccp.datasource.identifiers.wormbase.WormBaseID;
import edu.ucdenver.ccp.identifier.publication.DOI;
import edu.ucdenver.ccp.identifier.publication.PubMedID;

/**
 * @author Center for Computational Pharmacology, UC Denver;
 *         ccpsupport@ucdenver.edu
 * 
 */
public class GoaFileIdResolver implements IdResolver {

	private static final Logger logger = Logger.getLogger(GoaFileIdResolver.class);

	@Override
	public DataSourceIdentifier<?> resolveId(String idStr) {
		
		if (idStr.matches("RGD:\\d+")) {
			return new RgdID(idStr.substring(4));
		}
		if (idStr.startsWith("MGI:")) {
			return new MgiGeneID(idStr.substring(4));
		}
		if (idStr.startsWith("ZFIN:")) {
			return new ZfinID(idStr.substring(4));
		}
		if (idStr.startsWith("EMBL:")) {
			return new EmblID(idStr.substring(5));
		}
		if (idStr.startsWith("HGNC:")) {
			return new HgncID(idStr.substring(5));
		}
		if (idStr.startsWith("WB:")) {
			return new WormBaseID(idStr.substring(3));
		}
		if (idStr.startsWith("DOI:")) {
			return new DOI(idStr.substring(4));
		}
		if (idStr.matches("MP:\\d+")) {
			return new MammalianPhenotypeID(idStr);
		}
		if (idStr.matches("PMID:\\d+")) {
			return new PubMedID(idStr.substring(5));
		}
		if (idStr.matches("RDO:\\d+")) {
			return new RdoId(idStr);
		}
		if (idStr.matches("OMIM:\\d+")) {
			return new OmimID(idStr.substring(5));
		}
		if (idStr.matches("NCBI GeneID:\\d+")) {
			return new EntrezGeneID(idStr.substring(12));
		}
		if (idStr.matches("NBO:\\d+")) {
			return new NboId(idStr);
		}
		if (idStr.matches("PW:\\d+")) {
			return new PwId(idStr);
		}
		if (idStr.matches("GO:\\d+")) {
			return new GeneOntologyID(idStr);
		}
		if (idStr.matches("GO_REF:\\d+")) {
			return new GoRefID(idStr);
		}
		if (idStr.startsWith("Reactome:")) {
			return new ReactomeReactionID(idStr.substring(9));
		}
		if (idStr.startsWith("InterPro:")) {
			return new InterProID(idStr.substring(9));
		}
		if (StringUtil.startsWithRegex(idStr,"[Ee][Nn][Ss][Ee][Mm][Bb][Ll]:")) {
			return new EnsemblGeneID(idStr.substring(8));
		}
		if (idStr.startsWith("PANTHER:")) {
			return new PantherID(idStr.substring(8));
		}
		if (idStr.startsWith("HAMAP:")) {
			return new HAMAPId(idStr.substring(6));
		}
		if (idStr.startsWith("UniPathway:")) {
			return new UniPathwayId(idStr.substring(11));
		}
		if (idStr.startsWith("SGD:")) {
			return new SgdID(idStr.substring(4));
		}
		if (idStr.startsWith("RNAcentral:")) {
			return new RnaCentralId(idStr.substring(11));
		}
		if (idStr.startsWith("IntAct:")) {
			return new IntActID(idStr.substring(7));
		}
		if (idStr.startsWith("EC:")) {
			return new EnzymeCommissionID(idStr.substring(3));
		}
		if (idStr.startsWith("RefSeq:")) {
			return new RefSeqID(idStr.substring(7));
		}
		if (idStr.startsWith("PomBase:")) {
			return new PomBaseId(idStr.substring(8));
		}
		if (idStr.startsWith("PubChem_Compound:")) {
			return new PubChemCompoundId(idStr.substring(17));
		}
		if (idStr.startsWith("PubChem_Substance:")) {
			return new PubChemSubstanceId(idStr.substring(18));
		}
		if (idStr.startsWith("ECOGENE:")) {
			return new EcoGeneID(idStr.substring(8));
		}
		if (idStr.matches("CL:\\d+")) {
			return new CellTypeOntologyID(idStr);
		}
		if (idStr.matches("SO:\\d+")) {
			return new SequenceOntologyId(idStr);
		}
		if (idStr.matches("PR:\\d+")) {
			return new ProteinOntologyId(idStr);
		}
		if (idStr.startsWith("FB:")) {
			return new FlyBaseID(idStr);
		}
		if (idStr.startsWith("TAIR:")) {
			return new TairID(idStr);
		}
		if (idStr.startsWith("protein_id:")) {
			return ProteinAccessionResolver.resolveProteinAccession(idStr, null);
		}
		if (idStr.startsWith("NCBI_gi:")) {
			return new GiNumberID(idStr.substring(8));
		}
		if (idStr.startsWith("GenBank:")) {
			return new GenBankID(idStr.substring(8));
		}
		if (idStr.matches("CHEBI:\\d+")) {
			return new ChebiOntologyID(idStr);
		}
		if (idStr.matches("UBERON:\\d+")) {
			return new UberonID(idStr);
		}
		if (idStr.matches("NCBI_Gene:\\d+")) {
			return new EntrezGeneID(idStr.substring(10));
		}
		if (idStr.startsWith("UniProtKB:")) {
			if (idStr.contains(":PRO_")) {
				return new UnknownDataSourceIdentifier(idStr, null);
			}
			if (idStr.contains("-")) {
				return new UniProtIsoformID(idStr.substring(10));
			}
			return new UniProtID(idStr.substring(10));
		}
		
		logger.warn("Encountered unknown ID: " + idStr);
		return new UnknownDataSourceIdentifier(idStr, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ucdenver.ccp.datasource.identifiers.IdResolver#resolveId(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public DataSourceIdentifier<?> resolveId(String db, String id) {
		if (db.equals("RGD") && id.matches("\\d+")) {
			return new RgdID(id);
		}
		if (db.equals("UniProtKB")) {
			return new UniProtID(id);
		}
		logger.warn("Encountered unknown id/db: " + id + " -- " + db);
		return new UnknownDataSourceIdentifier(id, db);
	}

}
