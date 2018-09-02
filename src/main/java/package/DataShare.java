package com.s2rltx.projet.foodshuflle.application.model;

import java.util.List;
import java.util.stream.Collectors;

public class DataShare {
	private static volatile DataShare instance;

	private List<Integer> selectedRecetteIds;
	private int repas;

	/** Singletton. */
	private DataShare() {
	}

	public List<Integer> getSelectedRecetteIds() {
		return this.selectedRecetteIds;
	}

	public void setSelectedRecetteIds(List<String> selectedRecetteIds) {
		if (selectedRecetteIds == null) this.selectedRecetteIds = null;
		else this.selectedRecetteIds = selectedRecetteIds.stream().map(e -> Integer.parseInt(e)).collect(Collectors.toList());
	}
	
	

	public int getRepas() {
		return this.repas;
	}

	public void setRepas(String repas) {
		this.repas =Integer.parseInt(repas);
	}

	public static final DataShare instance() {
		if (DataShare.instance == null) {
			synchronized (DataShare.class) {
				if (DataShare.instance == null) {
					DataShare.instance = new DataShare();
				}
			}
		}

		return DataShare.instance;
	}
}
