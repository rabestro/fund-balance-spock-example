package lv.id.jc.workscope

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Subject(Zones)
@Title('Work zones')
@Narrative('collection of zones')
class ZonesTest extends Specification {
    def zone1 = Stub(WorkScope)
    def zone2 = Stub(WorkScope)
    def zone3 = Stub(WorkScope)

    def "should calculate total area of zones"() {
        given:
        zone1.area() >> area1
        zone2.area() >> area2
        zone3.area() >> area3

        when:
        def zones = Zones.of(zone1, zone2, zone3)

        then:
        zones.area() == expectedArea

        where:
        area1 | area2 | area3 | expectedArea
        12    | 30    | 22    | 64
        1     | 1     | 1     | 3
    }
}
