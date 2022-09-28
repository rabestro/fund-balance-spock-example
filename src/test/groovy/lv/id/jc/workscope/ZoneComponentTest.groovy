package lv.id.jc.workscope

import lv.id.jc.workcost.MaterialPrice
import lv.id.jc.workcost.WorkPrice
import lv.id.jc.workcost.ZonePrice
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title
import spock.lang.Unroll

@Title('Zone of work')
@Subject([Zone, Surface, MaterialPrice, WorkPrice, ZonePrice])
@Narrative('a component test that demonstrates the use of the Zone class')
class ZoneComponentTest extends Specification {

    @Unroll('should calculate all expenses per zone for #durationOfWork')
    def 'should calculate all expenses per zone'() {

        given: 'surface for painting'
        def totalSurface = Surface.square(squareSide)
        def apertures = Surface.zero()

        and: 'formulas for calculating the cost of putty and paint'
        def putty = MaterialPrice.of(8)
        def paint = MaterialPrice.of(7)

        and: 'formulas for calculating the cost of the relevant work'
        def puttying = WorkPrice.of(10)
        def painting = WorkPrice.of(15)

        and: 'formula for total expenses includes all other formulas'
        def expenses = ZonePrice.of(putty, paint, puttying, painting)

        when: 'we create a work zone with a specific surface and with a given cost'
        def zone = new Zone(expenses, totalSurface, apertures)

        then: 'we get a precisely calculated area of work'
        zone.area() == expectedArea

        and: 'we get a correctly and precisely calculated bill'
        zone.bill() == expectedBill

        where:
        squareSide || expectedArea | expectedBill                             | durationOfWork
        1          || 1            | expectedArea * (8 + 7 + 10 + 15)         | 'one day work'
        10         || 100          | expectedArea * (8 + 7 + 1.1 * (10 + 15)) | 'multi day work'
    }
}
