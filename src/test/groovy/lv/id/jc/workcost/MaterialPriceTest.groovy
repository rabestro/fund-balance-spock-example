package lv.id.jc.workcost


import lv.id.jc.workscope.Surface
import spock.lang.Issue
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title


@Title('Project Customer calculates the price of the material')
@Narrative('''
As the customer of the project 
I want to calculate the price of the material for the surface
So that I be able to calculate the total cost of processing the zone
''')
@Issue('12')
@Subject(MaterialPrice)
class MaterialPriceTest extends PriceSpecification {

    def "should calculate the price of materials with default material area factor"() {

        given: 'surface for processing with a given area'
        surface.area() >> surfaceArea

        when: 'we create a function for calculating the cost of material with default area factor'
        def priceFunction = MaterialPrice.norm()

        then: 'we get accurately calculated the cost of the material for the surface'
        priceFunction.apply(surface) == expectedPrice

        where:
        surfaceArea | expectedPrice
        0           | 0
        1           | 10
        10.2        | 102
    }

    def "should calculate the price of materials with certain material area factor"() {

        given: 'surface for processing with a given area'
        surface.area() >> surfaceArea

        when: 'we create a function with certain material area factor'
        def priceFunction = MaterialPrice.of(materialAreaFactor)

        then: 'we get accurately calculated the cost of the material for the surface'
        priceFunction.apply(surface) == expectedPrice

        where:
        materialAreaFactor | surfaceArea | expectedPrice
        0                  | 0           | 0
        0                  | 100         | 0
        1                  | 1           | 1
        1                  | 0.1         | 0.1
        3                  | 0.1         | 0.3
        10                 | 1           | 10
    }
}
