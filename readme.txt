BeerMe! A beer customizer

This is a toy program to demonstrate use of the Actor model
for distributed and concurrent computing.

BeerMe! takes a list of a customer's desired ingredients,
brews the beer based on their specifications and ships it to them.

An order consists of:
1. Wet hops
2. Dry hops (optional)
3. Grain Bill
4. Filtered or unfiltered
5. Conditioning options (duration, temperature, barrel aged)
6. Keg size (by volume)

Beer consists of
1. Hops (see https://www.beeradvocate.com/beer/101/hops/)
	a. Amarillo
	b. Cascade
	c. Centennial
	d. Chinook
	e. Fuggle
	f. Perle
	g. Saaz
	h. Hallertau
	i. Golding
	
	Hops also have an alpha and beta acid level, which contributes to the IBU

3. Grain Bill (Malted Barley + corn, sorghum, rye, wheat, oatmeal)
	
4. Yeast
	a. Saccharomyces cerevisiae, ale yeast, top-fermenting, warm fermentation
	b. Saccharomyces pastorianus, lager yeast, bottom-fermenting, cool fermentation
	c. Brettanomyces, lambic yeast, spontaneous (wild) fermentation
	d. Torulaspora delbrueckii, weissbier yeast, cool fermentation

Optional Processes
	1. Dry-hopping, for IPAs
	2. Aged at temperature, for Pilsners
	3. Aged in bourbon barrel
	4. Lagering

1. An order-taker accepts the order
2. A warehouse manager checks stock on the ingredients and updates supply
3. A brewer starts the brew process
	a. Malting -> Malt
		i. Steeping
		ii. Germination
		iii. Kilning
	b. Milling
	b. Mashing -> Wort, performed in MashTun, 1-2 hours
	c. Lautering, in the LauterTun, 
		i. Run-off
		ii. Sparging (rinsing grains with hot water)
	d. Boiling performed in Kettle, Hops added at this stage, 90 min
	d. Whirlpool, separates out sediment, Cooled in a heat exchanger
	e. Fermenting, in Fermentation Tank, yeast is "pithed" (added) here, optional dry hopping performed at this stage
		i. Warm Method
		ii. Cool Method
		iii. Spontaneous Method
	f. Conditioning -> Beer, in ConditioningTank, Aging?, weeks or months
	f. Pasteurization?
	g. Filtering
	h. Casking or bottling, optional dry hopping performed at this stage
4. A shipper sends the keg back to the customer

The customer should be notified of when their order will ship,
and calculate the IBU's based on their selection.
(see "http://www.homebrewing.org/International-Bittering-Units_ep_49-1.html")


