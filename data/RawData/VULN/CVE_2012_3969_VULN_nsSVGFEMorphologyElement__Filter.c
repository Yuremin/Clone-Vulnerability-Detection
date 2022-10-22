nsresult
CVE_2012_3969_VULN_nsSVGFEMorphologyElement::Filter(nsSVGFilterInstance *instance,
                                 const nsTArray<const Image*>& aSources,
                                 const Image* aTarget,
                                 const nsIntRect& rect)
{
  PRInt32 rx, ry;
  GetRXY(&rx, &ry, *instance);

  if (rx < 0 || ry < 0) {
    // XXX nsSVGUtils::ReportToConsole()
    return NS_OK;
  }
  if (rx == 0 && ry == 0) {
    return NS_OK;
  }

  PRUint8* sourceData = aSources[0]->mImage->Data();
  PRUint8* targetData = aTarget->mImage->Data();
  PRUint32 stride = aTarget->mImage->Stride();
  PRUint8 extrema[4];         // RGBA magnitude of extrema
  PRUint16 op = mEnumAttributes[OPERATOR].GetAnimValue();

  // Scan the kernel for each pixel to determine max/min RGBA values.
  for (PRInt32 y = rect.y; y < rect.YMost(); y++) {
    PRUint32 startY = NS_MAX(0, y - ry);
    // We need to read pixels not just in 'rect', which is limited to
    // the dirty part of our filter primitive subregion, but all pixels in
    // the given radii from the source surface, so use the surface size here.
    PRUint32 endY = NS_MIN(y + ry, instance->GetSurfaceHeight() - 1);
    for (PRInt32 x = rect.x; x < rect.XMost(); x++) {
      PRUint32 startX = NS_MAX(0, x - rx);
      PRUint32 endX = NS_MIN(x + rx, instance->GetSurfaceWidth() - 1);
      PRUint32 targIndex = y * stride + 4 * x;

      for (PRUint32 i = 0; i < 4; i++) {
        extrema[i] = sourceData[targIndex + i];
      }
      for (PRUint32 y1 = startY; y1 <= endY; y1++) {
        for (PRUint32 x1 = startX; x1 <= endX; x1++) {
          for (PRUint32 i = 0; i < 4; i++) {
            PRUint8 pixel = sourceData[y1 * stride + 4 * x1 + i];
            if ((extrema[i] > pixel &&
                 op == nsSVGFEMorphologyElement::SVG_OPERATOR_ERODE) ||
                (extrema[i] < pixel &&
                 op == nsSVGFEMorphologyElement::SVG_OPERATOR_DILATE)) {
              extrema[i] = pixel;
            }
          }
        }
      }
      targetData[targIndex  ] = extrema[0];
      targetData[targIndex+1] = extrema[1];
      targetData[targIndex+2] = extrema[2];
      targetData[targIndex+3] = extrema[3];
    }
  }
  return NS_OK;
}
